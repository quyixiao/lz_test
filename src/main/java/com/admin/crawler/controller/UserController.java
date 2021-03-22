package com.admin.crawler.controller;


import com.admin.crawler.constant.CacheConstants;
import com.admin.crawler.dto.test.LoginResp;
import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.dto.test.UserLoginDto;
import com.admin.crawler.entity.GitlabUser;
import com.admin.crawler.service.GitlabUserService;
import com.admin.crawler.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private GitlabUserService gitlabUserService;


    @Autowired
    private RedisCacheUtil redisCacheUtil;


    @RequestMapping("/login")
    public R login(@RequestBody UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if (StringUtil.isBlank(username)) {
            return R.error("用户名不能为空");
        }
        if (StringUtil.isBlank(password)) {
            return R.error("密码不能为空");
        }
        LoginResp resp = new LoginResp();
        resp.setSpaceName(" 测试");
        resp.setUserName(userLoginDto.getUsername());
        GitlabUser gitlabUser = gitlabUserService.selectGitlabUserByUsername(username);
        if (gitlabUser != null && gitlabUser.getPassword().equals(MD5Util.encode(password))) {
            log.info("登陆成功=======");
            resp.setUserId(gitlabUser.getId());
            resp.setToken(OrderUtil.getUserPoolOrder(gitlabUser.getId() + ""));

            saveRedis(gitlabUser, resp.getToken());
            return R.ok("登陆成功").putData(resp);
        }
        return R.error("登陆失败");
    }


    @RequestMapping("/getUserInfo")
    public R getUserInfo(@RequestBody UserLoginDto userLoginDto) {
        Object object  =  redisCacheUtil.getObject(userLoginDto.getToken());
        if(object !=null){
            UserInfo userInfo = (UserInfo)object;
            return R.ok("获取用户信息成功").putData(userInfo);
        }
        return R.error("获取用户失败");
    }

    public void saveRedis(GitlabUser gitlabUser, String token) {
        UserInfo userInfo = new UserInfo();
        userInfo.setMobile(gitlabUser.getUsername());
        userInfo.setUsername(gitlabUser.getUsername());
        userInfo.setToken(token);
        userInfo.setUserId(gitlabUser.getId());
        redisCacheUtil.saveObject(token, userInfo, CacheConstants.SECOND_OF_ONE_MONTH);
    }

    @RequestMapping("/test")
    public R init(@RequestBody UserLoginDto userLoginDto) {
        log.info("---------------\n" + userLoginDto.getCode());
        return R.ok();
    }


}
