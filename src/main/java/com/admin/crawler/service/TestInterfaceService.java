package com.admin.crawler.service;

import com.admin.crawler.dto.test.InterfaceReq;
import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.utils.R;

import java.util.List;

/**
 * <p>
 * 接口 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
public interface TestInterfaceService {


    TestInterface selectTestInterfaceById(Long id);


    Long insertTestInterface(TestInterface testInterface);


    int updateTestInterfaceById(TestInterface testInterface);


    int updateCoverTestInterfaceById(TestInterface testInterface);


    int deleteTestInterfaceById(Long id);


    R selectListBy(InterfaceReq req);

    R doAddOrUpdate(InterfaceReq req);

    TestInterface selectBySpaceIdMenuIdFileName(Long menuId, String path);

    List<TestInterface> selectByIds(List<Long> ids);

    R getGroupInfo(InterfaceReq req);
}