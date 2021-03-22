package com.admin.crawler.controller;


import com.admin.crawler.dto.test.SysSpaceDto;
import com.admin.crawler.entity.SysSpace;
import com.admin.crawler.exception.LZException;
import com.admin.crawler.service.SysMenuService;
import com.admin.crawler.service.SysSpaceService;
import com.admin.crawler.utils.R;
import com.admin.crawler.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysSpace")
public class SysSpaceController {


    @Autowired
    private SysSpaceService sysSpaceService;


    @Autowired
    private SysMenuService sysMenuService;


    @RequestMapping("/list")
    public R list() {
        List<SysSpace> sysSpaceList = sysSpaceService.selectAll();
        return R.ok().putData(sysSpaceList);
    }


    @RequestMapping("/getById")
    public R getById(@RequestBody SysSpace sysSpace) {
        return R.ok().put("sysSpace", sysSpace);
    }


    @PostMapping("/saveOrUpdate")
    public R update(@RequestBody SysSpaceDto dto) {
        SysSpace sysSpace = new SysSpace();
        BeanUtils.copyProperties(dto, sysSpace);
        sysSpace.setGitProjectName(getProjectName(dto.getGitlabUrl()));
        //sysSpace.setGitProjectId(gitService.getProjectIdByProjectName(sysSpace.getGitProjectName()).longValue());
        if (dto.getId() != null) {
            sysSpaceService.updateSysSpaceById(sysSpace);

            return R.ok();
        }
        sysSpaceService.insertSysSpace(sysSpace);

        return R.ok();
    }

    private String getProjectName(String gitUrl) {
        if (StringUtil.isBlank(gitUrl)) {
            throw new LZException("项目地址路径错误");
        }
        int i = gitUrl.lastIndexOf("/");
        int j = gitUrl.indexOf(".git");
        if (i < 1 || j < 1) {
            throw new LZException("项目地址路径错误");
        }
        return gitUrl.substring(i + 1, j);
    }

}
