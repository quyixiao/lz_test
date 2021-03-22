package com.admin.crawler.service;

import com.admin.crawler.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-05
 */
public interface SysMenuService {


    SysMenu selectSysMenuById(Long id);


    Long insertSysMenu(SysMenu sysMenu);


    int updateSysMenuById(SysMenu sysMenu);


    int updateCoverSysMenuById(SysMenu sysMenu);


    int deleteSysMenuById(Long id);

    List<SysMenu> getTreeBySpaceId(Long spaceId);


    SysMenu selectSysMenuByNameAndParentIdAndSpaceId(String name, Long parentId, Long spaceId);
}