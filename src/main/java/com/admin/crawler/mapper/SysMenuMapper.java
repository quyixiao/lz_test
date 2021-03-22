package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 菜单管理 服务类
* </p>
*
* @author ququququ
* @since 2021-01-05
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


	SysMenu selectSysMenuById(@Param("id") Long id);


	Long insertSysMenu(SysMenu sysMenu);


	int updateSysMenuById(SysMenu sysMenu);


	int updateCoverSysMenuById(SysMenu sysMenu);


	int deleteSysMenuById(@Param("id") Long id);

	List<SysMenu> getSysMenusBySpaceIdAndParentId(@Param("spaceId") Long spaceId, @Param("parentId") Long parentId);


	List<SysMenu> getSysMenusBySpaceId(@Param("spaceId") Long spaceId);

    SysMenu selectSysMenuByNameAndParentIdAndSpaceId(@Param("name") String name,
                                                     @Param("parentId") Long parentId,
                                                     @Param("spaceId") Long spaceId);
}