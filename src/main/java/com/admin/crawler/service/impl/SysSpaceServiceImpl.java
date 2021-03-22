package com.admin.crawler.service.impl;

import com.admin.crawler.entity.SysSpace;
import com.admin.crawler.mapper.SysSpaceMapper;
import com.admin.crawler.service.SysSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 空间管理 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-01-05
 */

@Service
public class SysSpaceServiceImpl implements SysSpaceService {


    @Autowired
    private SysSpaceMapper sysSpaceMapper;


    @Override
    public SysSpace selectSysSpaceById(Long id) {
        return sysSpaceMapper.selectSysSpaceById(id);
    }


    @Override
    public Long insertSysSpace(SysSpace sysSpace) {
        return sysSpaceMapper.insertSysSpace(sysSpace);
    }


    @Override
    public int updateSysSpaceById(SysSpace sysSpace) {
        return sysSpaceMapper.updateSysSpaceById(sysSpace);
    }


    @Override
    public int updateCoverSysSpaceById(SysSpace sysSpace) {
        return sysSpaceMapper.updateCoverSysSpaceById(sysSpace);
    }


    @Override
    public int deleteSysSpaceById(Long id) {
        return sysSpaceMapper.deleteSysSpaceById(id);
    }

    @Override
    public List<SysSpace> selectAll() {

        return sysSpaceMapper.selectAll();
    }


}
