package com.admin.crawler.service;

import com.admin.crawler.entity.TestGroupInterface;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组接口 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
public interface TestGroupInterfaceService {


    TestGroupInterface selectTestGroupInterfaceById(Long id);


    Long insertTestGroupInterface(TestGroupInterface testGroupInterface);


    int updateTestGroupInterfaceById(TestGroupInterface testGroupInterface);


    int updateCoverTestGroupInterfaceById(TestGroupInterface testGroupInterface);


    int deleteTestGroupInterfaceById(Long id);


    List<TestGroupInterface> selectByGroupId(@Param("id") Long id);

    List<TestGroupInterface> selectByGroupIdInterFaceIds(Long id, List<Long> ids);

    List<TestGroupInterface> selectByGroupIdInterFaceIdsIsDisable(Long groupId, List<Long> interfaceIds, int isDisable);

    List<TestGroupInterface> selectByMenuId(Long menuId);


    List<TestGroupInterface> selectByInterfaceId(Long interfaceId);

    List<TestGroupInterface> selectByGroupIdOrderByPosition(Long groupId);
}