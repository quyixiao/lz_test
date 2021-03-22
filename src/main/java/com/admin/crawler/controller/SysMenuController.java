package com.admin.crawler.controller;


import com.admin.crawler.dto.test.SpaceDto;
import com.admin.crawler.dto.test.SysMenuDto;
import com.admin.crawler.entity.SysMenu;
import com.admin.crawler.service.SysMenuService;
import com.admin.crawler.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysMenu")
@Slf4j
public class SysMenuController extends BaseController {


    @Autowired
    private SysMenuService sysMenuService;


    @RequestMapping("/getTreeBySpaceId")
    public R list(@RequestBody SpaceDto spaceDto) {

        List<SysMenu> data = sysMenuService.getTreeBySpaceId(spaceDto.getSpaceId());


        return R.ok().putData("list", data);
    }


    @RequestMapping("/getById")
    public R getById(@RequestBody SysMenu sysMenu) {
        return R.ok().put("sysMenu", sysMenu);
    }


    @RequestMapping("/delete")
    public R delete(@RequestBody SysMenu sysMenu) {
        sysMenuService.deleteSysMenuById(sysMenu.getId());
        return R.ok();
    }


    @RequestMapping("/saveOrUpdate")
    public R update(@RequestBody SysMenuDto dto) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        if (dto.getId() != null) {
            sysMenuService.updateSysMenuById(sysMenu);
            return R.ok();
        }
        //这里判断有没有重名
        SysMenu sysMenu1 = sysMenuService.selectSysMenuByNameAndParentIdAndSpaceId(dto.getName(), dto.getParentId(), dto.getSpaceId());
        if (sysMenu1 != null) {
            log.info("该菜单下添加菜单重名{}", dto.getName());
            return R.error("该菜单下已经存在同名菜单");
        }

        sysMenuService.insertSysMenu(sysMenu);
        if (!sysMenu.getUrl().contains("?")) {
            sysMenu.setUrl(sysMenu.getUrl() + "?id=" + sysMenu.getId());
            sysMenuService.updateSysMenuById(sysMenu);
        }
        return R.ok().put("id", sysMenu.getId());
    }


}
