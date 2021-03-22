package com.admin.crawler.service.impl;

import com.admin.crawler.entity.TestInterfaceModified;
import com.admin.crawler.mapper.TestInterfaceModifiedMapper;
import com.admin.crawler.service.TestInterfaceModifiedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接口修改记录表 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
public class TestInterfaceModifiedServiceImpl implements TestInterfaceModifiedService {


    @Autowired
    private TestInterfaceModifiedMapper testInterfaceModifiedMapper;


    @Override
    public TestInterfaceModified selectTestInterfaceModifiedById(Long id) {
        return testInterfaceModifiedMapper.selectTestInterfaceModifiedById(id);
    }


    @Override
    public Long insertTestInterfaceModified(TestInterfaceModified testInterfaceModified) {
        return testInterfaceModifiedMapper.insertTestInterfaceModified(testInterfaceModified);
    }


    @Override
    public int updateTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified) {
        return testInterfaceModifiedMapper.updateTestInterfaceModifiedById(testInterfaceModified);
    }


    @Override
    public int updateCoverTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified) {
        return testInterfaceModifiedMapper.updateCoverTestInterfaceModifiedById(testInterfaceModified);
    }


    @Override
    public int deleteTestInterfaceModifiedById(Long id) {
        return testInterfaceModifiedMapper.deleteTestInterfaceModifiedById(id);
    }


}
