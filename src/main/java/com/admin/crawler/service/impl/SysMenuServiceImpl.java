package com.admin.crawler.service.impl;

import com.admin.crawler.entity.SysMenu;
import com.admin.crawler.mapper.SysMenuMapper;
import com.admin.crawler.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-05
 */

@Service
public class SysMenuServiceImpl implements SysMenuService {


    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public SysMenu selectSysMenuById(Long id) {
        return sysMenuMapper.selectSysMenuById(id);
    }


    @Override
    public Long insertSysMenu(SysMenu sysMenu) {
        return sysMenuMapper.insertSysMenu(sysMenu);
    }


    @Override
    public int updateSysMenuById(SysMenu sysMenu) {
        return sysMenuMapper.updateSysMenuById(sysMenu);
    }


    @Override
    public int updateCoverSysMenuById(SysMenu sysMenu) {
        return sysMenuMapper.updateCoverSysMenuById(sysMenu);
    }


    @Override
    public int deleteSysMenuById(Long id) {
        return sysMenuMapper.deleteSysMenuById(id);
    }


    @Override
    public List<SysMenu> getTreeBySpaceId(Long spaceId) {
        Long parentId = 0L;
        List<SysMenu> sysMenusAll = sysMenuMapper.getSysMenusBySpaceId(spaceId);
        List<SysMenu> sysMenus = getSysMenusBySpaceIdAndParentId(sysMenusAll, parentId);
        if (sysMenus ==null || sysMenus.size() ==0 ) {
            return Collections.EMPTY_LIST;
        }
        return getMenuTreeList(sysMenusAll, sysMenus);
    }


    public List<SysMenu> getSysMenusBySpaceIdAndParentId(List<SysMenu> sysMenuList, Long parentId) {
        List<SysMenu> sysMenus = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId().equals(parentId)) {
                sysMenus.add(sysMenu);
            }
        }
        return sysMenus;
    }


    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> sysMenusAll, List<SysMenu> menuList) {
        List<SysMenu> subMenuList = new ArrayList<>();

        for (SysMenu entity : menuList) {
            //目录
            entity.setList(getMenuTreeList(sysMenusAll, getSysMenusBySpaceIdAndParentId(sysMenusAll, entity.getId())));

            subMenuList.add(entity);
        }

        return subMenuList;
    }

    @Override
    public SysMenu selectSysMenuByNameAndParentIdAndSpaceId(String name, Long parentId, Long spaceId) {
        return sysMenuMapper.selectSysMenuByNameAndParentIdAndSpaceId(name, parentId, spaceId);
    }


}
