package com.admin.crawler.service;

import com.admin.crawler.dto.test.GroupReq;
import com.admin.crawler.entity.TestGroup;
import com.admin.crawler.utils.R;

import java.util.List;

/**
 * <p>
 * 测试组 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
public interface TestGroupService {


    TestGroup selectTestGroupById(Long id);


    Long insertTestGroup(TestGroup testGroup);


    int updateTestGroupById(TestGroup testGroup);


    int updateCoverTestGroupById(TestGroup testGroup);


    int deleteTestGroupById(Long id);


    R selectListBy(GroupReq req);

    R doAddOrUpdate(GroupReq req);


    R doAddInterface(GroupReq req);

    R interfaceList(GroupReq req);

    R interfaceUpdate(GroupReq req);

    void groupTest(GroupReq req);

    List<TestGroup> selectByIds(List<Long> list);
}