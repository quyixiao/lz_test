package com.admin.crawler.service;

import com.admin.crawler.entity.SysSpace;

import java.util.List;

/**
 * <p>
 * 空间管理 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-05
 */
public interface SysSpaceService {


    SysSpace selectSysSpaceById(Long id);


    Long insertSysSpace(SysSpace sysSpace);


    int updateSysSpaceById(SysSpace sysSpace);


    int updateCoverSysSpaceById(SysSpace sysSpace);


    int deleteSysSpaceById(Long id);


    List<SysSpace> selectAll();
}