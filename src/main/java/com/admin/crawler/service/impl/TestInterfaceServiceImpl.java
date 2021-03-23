package com.admin.crawler.service.impl;

import com.admin.crawler.aop.LogAspect;
import com.admin.crawler.bo.TestInterfaceDto;
import com.admin.crawler.dto.test.InterfaceReq;
import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.entity.TestGroup;
import com.admin.crawler.entity.TestGroupInterface;
import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.entity.TestInterfaceModified;
import com.admin.crawler.mapper.TestGroupInterfaceMapper;
import com.admin.crawler.mapper.TestInterfaceMapper;
import com.admin.crawler.service.*;
import com.admin.crawler.utils.MD5Util;
import com.admin.crawler.utils.NumberUtil;
import com.admin.crawler.utils.R;
import com.admin.crawler.utils.StringUtil;
import com.lz.mybatis.plugin.utils.t.PluginTuple;
import com.lz.mybatis.plugin.utils.t.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 接口 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
@Slf4j
public class TestInterfaceServiceImpl implements TestInterfaceService {


    @Autowired
    private TestInterfaceMapper testInterfaceMapper;


    @Autowired
    private TestInterfaceModifiedService testInterfaceModifiedService;

    @Autowired
    private TestGroupInterfaceService testGroupInterfaceService;

    @Autowired
    private TestGroupService testGroupService;

    @Autowired
    private TestGroupInterfaceMapper testGroupInterfaceMapper;


    @Autowired
    private GitOperationService git;

    public static final String MASTER = "master";

    @Override
    public TestInterface selectTestInterfaceById(Long id) {
        return testInterfaceMapper.selectTestInterfaceById(id);
    }


    @Override
    public Long insertTestInterface(TestInterface testInterface) {
        return testInterfaceMapper.insertTestInterface(testInterface);
    }


    @Override
    public int updateTestInterfaceById(TestInterface testInterface) {
        return testInterfaceMapper.updateTestInterfaceById(testInterface);
    }


    @Override
    public int updateCoverTestInterfaceById(TestInterface testInterface) {
        return testInterfaceMapper.updateCoverTestInterfaceById(testInterface);
    }


    @Override
    public int deleteTestInterfaceById(Long id) {
        return testInterfaceMapper.deleteTestInterfaceById(id);
    }

    @Override
    public R selectListBy(InterfaceReq req) {
        List<TestInterface> list = testInterfaceMapper.selectAll(req.getMenuId(), req.getName());
        List<TestInterfaceDto> testInterfaceDtos = new ArrayList<>();
        List<TestGroupInterface> testGroupInterfaceList = testGroupInterfaceService.selectByMenuId(req.getMenuId());

        Map<Long, Set<Long>> map = new HashMap<>();
        for (TestGroupInterface testGroupInterface : testGroupInterfaceList) {
            Set<Long> set = map.get(testGroupInterface.getInterfaceId());
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(NumberUtil.objToLong(testGroupInterface.getGroupId()));
            map.put(testGroupInterface.getInterfaceId(), set);
        }
        Map<Long, Integer> integerMap = new HashMap<>();
        for (TestGroupInterface testGroupInterface : testGroupInterfaceList) {
            if (testGroupInterface.getGroupId().equals(req.getGroupId())) {
                Integer num = integerMap.get(testGroupInterface.getInterfaceId());
                if (num == null) {
                    num = 0;
                }
                integerMap.put(testGroupInterface.getInterfaceId(), num + 1);
            }
        }
        for (TestInterface testInterface : list) {
            TestInterfaceDto testInterfaceDto = new TestInterfaceDto();
            BeanUtils.copyProperties(testInterface, testInterfaceDto);
            Set set = map.get(testInterface.getId());
            if (set != null) {
                testInterfaceDto.setGroupRefreshNum(set.size());
            }
            testInterfaceDto.setCode(null);
            testInterfaceDto.setCurGroupRefreshNum(NumberUtil.objToIntDefault(integerMap.get(testInterface.getId()), 0));
            testInterfaceDtos.add(testInterfaceDto);
        }
        return R.ok().putData(testInterfaceDtos);
    }

    @Override
    public R doAddOrUpdate(InterfaceReq req) {
        UserInfo userInfo = LogAspect.THREAD_LOCAL.get();
        TestInterface testInterface = testInterfaceMapper.selectTestInterfaceById(req.getId());
        String directory = StringUtil.getSimplePath(testInterface != null ? testInterface.getGmtCreate() : null);
        String branch = userInfo.getUsername();
        if (testInterface != null) {                                  //是否在解决冲突
            String code = req.getCode();
            String md51 = MD5Util.encode(testInterface.getCode());
            String md52 = MD5Util.encode(req.getCode());
            if (!md51.equals(md52)) {     //只有代码被改动了，才进行 git 操作
                log.info("代码被改动，进行 git 合并操作");
                Tuple2<R,String> data = gitOperation(branch, directory, testInterface.getGitFileName(), req.getCode()).getData();
                if (NumberUtil.objToIntDefault(data.getFirst().get("code"), 0) != 1000) {
                    return data.getFirst();
                }
                code = data.getSecond();                //获取合并之后的代码
            }
            testInterface.setFileName(req.getFileName());
            testInterface.setName(req.getName());
            if (StringUtil.isNotBlank(req.getFileName())) {
                TestInterface t = testInterfaceMapper.selectBySpaceIdMenuIdFileName(req.getMenuId(), req.getFileName());
                if (t != null) {
                    return R.error("文件名己经存在");
                }
            }
            testInterface.setFileName(req.getFileName());
            testInterface.setIsDelete(req.getIsDelete());
            testInterface.setCode(code);
            testInterfaceMapper.updateTestInterfaceById(testInterface);
            List<TestGroupInterface> interfaces = testGroupInterfaceMapper.selectByInterfaceId(testInterface.getId());
            if (interfaces != null && interfaces.size() > 0) {
                for (TestGroupInterface testGroupInterface : interfaces) {
                    testGroupInterface.setName(testInterface.getName());
                }
                testGroupInterfaceMapper.updateBatch(interfaces);
            }
        } else {
            testInterface = new TestInterface();
            testInterface.setCode(req.getCode());
            testInterface.setFileName(req.getFileName());
            testInterface.setName(req.getName());
            Integer max = testInterfaceMapper.selectMaxPosition(req.getMenuId());
            testInterface.setPosition(max + 1);
            testInterface.setRealName(userInfo.getRealName());
            testInterface.setMenuId(req.getMenuId());
            testInterface.setSpaceId(LogAspect.SPACE_ID.get());
            if (StringUtil.isNotBlank(req.getFileName())) {
                TestInterface t = testInterfaceMapper.selectBySpaceIdMenuIdFileName(req.getMenuId(), req.getFileName());
                if (t != null) {
                    return R.error("文件名己经存在");
                }
            }
            testInterface.setFileName(req.getFileName());
            testInterface.setUserId(userInfo.getUserId());
            testInterfaceMapper.insertTestInterface(testInterface);
            testInterface.setGitFileName(testInterface.getId() + "test.tsh");
            testInterfaceMapper.updateTestInterfaceById(testInterface);
            gitOperation(branch, directory, testInterface.getGitFileName(), testInterface.getCode());      //添加时绝对没有问题
        }

        TestInterfaceModified testInterfaceModified = new TestInterfaceModified();      //保存接口修改记录
        testInterfaceModified.setCode(testInterface.getCode());
        testInterfaceModified.setRealName(userInfo.getRealName());
        testInterfaceModified.setUserId(userInfo.getUserId());
        testInterfaceModifiedService.insertTestInterfaceModified(testInterfaceModified);
        return R.ok();
    }


    //需要注意的是每一个用户名都会有一个自己分支，每次都切换到自己的分支，将编辑的代码和 master 代码合并，如果
    //冲突，冲突的代码返回给前端，前端解决冲突，即可保存代码，并提交，再合并到 master 中，再提交远程
    public PluginTuple gitOperation(String branch, String directory, String fileName, String code) {
        try {
            // 拉取 master 代码 , 恢复 master 代码
            String currBranch = git.getBranch();     //获取当前本地分支
            if (MASTER.equals(currBranch)) {          // 如果当前分支是 master分支
                git.fetch();
                git.reset(MASTER);
                git.add();
                git.commit("提交修改");//为了保险起见，还是 git -am "提交修改" 一下

                git.checkout(branch);   //切换到用户自己分支
                git.pull();
            } else if (branch.equals(currBranch)) {        //如果当前分支是自己之前所在分支，可能用户正在解决冲突
                //...                                      //什么都不做
            } else {      // 如果是在别人的账户下,可能别人的代码也冲突了，因此需要恢复别人的代码，再切换到自己所在分支
                git.add();
                git.commit("切换到" + branch + "分支,先提交代码");

                git.checkout(branch);
                git.pull();
            }
            git.write(directory, fileName, code); // 写入文件
            git.add(directory + "/" + fileName);  // 提交推送到远程
            git.commit("提交本次修改");
            String status = git.merge(MASTER);    // 合并 master 文件
            if ("Conflicting".equals(status)) {
                String content = git.read(directory, fileName);
                R r = R.error("代码冲突").putData("code", content);
                r.put("code", 300);
                return new PluginTuple(r,content);
            }
            git.push(branch); //如果代码没有冲突，将分支代码推送到远程
            git.checkout(MASTER);  //切换到 master分支
            git.pull();
            git.merge(branch);      //合并分支代码
            git.push(MASTER);     //推送master到远程
            String content = git.read(directory, fileName);
            return new PluginTuple(R.ok(),content);
        } catch (Exception e) {
            log.error("git 操作文件异常", e);
        }
        return new PluginTuple(R.error("git操作异常"));
    }


    @Override
    public TestInterface selectBySpaceIdMenuIdFileName(Long menuId, String fileName) {
        return testInterfaceMapper.selectBySpaceIdMenuIdFileName(menuId, fileName);
    }

    @Override
    public List<TestInterface> selectByIds(List<Long> ids) {

        return testInterfaceMapper.selectByIds(ids);
    }


    @Override
    public R getGroupInfo(InterfaceReq req) {
        List<TestGroupInterface> testGroupInterfaces = testGroupInterfaceService.selectByInterfaceId(req.getId());
        List<TestGroup> groupList = new ArrayList<>();
        if (testGroupInterfaces != null && testGroupInterfaces.size() > 0) {
            List<Long> list = new ArrayList<>();
            for (TestGroupInterface testGroupInterface : testGroupInterfaces) {
                list.add(testGroupInterface.getGroupId());
            }
            groupList = testGroupService.selectByIds(list);
        }

        return R.ok().put("data", groupList);
    }


}
