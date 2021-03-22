package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.SysSpace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 空间管理 服务类
* </p>
*
* @author ququququ
* @since 2021-01-05
*/
@Mapper
public interface SysSpaceMapper extends BaseMapper<SysSpace> {


	SysSpace selectSysSpaceById(@Param("id") Long id);


	Long insertSysSpace(SysSpace sysSpace);


	int updateSysSpaceById(SysSpace sysSpace);


	int updateCoverSysSpaceById(SysSpace sysSpace);


	int deleteSysSpaceById(@Param("id") Long id);


    List<SysSpace> selectAll();
}