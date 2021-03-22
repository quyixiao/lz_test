package com.admin.crawler.service.impl;

import com.admin.crawler.entity.TestGroupInterface;
import com.admin.crawler.mapper.TestGroupInterfaceMapper;
import com.admin.crawler.service.TestGroupInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组接口 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
public class TestGroupInterfaceServiceImpl implements TestGroupInterfaceService {


    @Autowired
    private TestGroupInterfaceMapper testGroupInterfaceMapper;


    @Override
    public TestGroupInterface selectTestGroupInterfaceById(Long id) {
        return testGroupInterfaceMapper.selectTestGroupInterfaceById(id);
    }


    @Override
    public Long insertTestGroupInterface(TestGroupInterface testGroupInterface) {
        return testGroupInterfaceMapper.insertTestGroupInterface(testGroupInterface);
    }


    @Override
    public int updateTestGroupInterfaceById(TestGroupInterface testGroupInterface) {
        return testGroupInterfaceMapper.updateTestGroupInterfaceById(testGroupInterface);
    }


    @Override
    public int updateCoverTestGroupInterfaceById(TestGroupInterface testGroupInterface) {
        return testGroupInterfaceMapper.updateCoverTestGroupInterfaceById(testGroupInterface);
    }


    @Override
    public int deleteTestGroupInterfaceById(Long id) {
        return testGroupInterfaceMapper.deleteTestGroupInterfaceById(id);
    }

    @Override
    public List<TestGroupInterface> selectByGroupId(Long id) {

        return testGroupInterfaceMapper.selectByGroupId(id);
    }

    @Override
    public List<TestGroupInterface> selectByGroupIdInterFaceIds(Long menuId, List<Long> interfaceIds) {
        return testGroupInterfaceMapper.selectByGroupIdInterFaceIds(menuId, interfaceIds);
    }

    @Override
    public List<TestGroupInterface> selectByGroupIdInterFaceIdsIsDisable(Long groupId, List<Long> interfaceIds, int isDisable) {
        return testGroupInterfaceMapper.selectByGroupIdInterFaceIdsIsDisable(groupId, interfaceIds, isDisable);
    }

    @Override
    public List<TestGroupInterface> selectByMenuId(Long menuId) {

        return testGroupInterfaceMapper.selectByMenuId(menuId);
    }

    @Override
    public List<TestGroupInterface> selectByInterfaceId(Long interfaceId) {
        return testGroupInterfaceMapper.selectByInterfaceId(interfaceId);
    }

    @Override
    public List<TestGroupInterface> selectByGroupIdOrderByPosition(Long groupId) {
        return testGroupInterfaceMapper.selectByGroupIdOrderByPosition(groupId);
    }


}
