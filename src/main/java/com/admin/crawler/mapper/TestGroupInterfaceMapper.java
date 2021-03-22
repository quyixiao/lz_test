package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.TestGroupInterface;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface TestGroupInterfaceMapper extends BaseMapper<TestGroupInterface> {


    TestGroupInterface selectTestGroupInterfaceById(@Param("id") Long id);


    Long insertTestGroupInterface(TestGroupInterface testGroupInterface);


    int updateTestGroupInterfaceById(TestGroupInterface testGroupInterface);


    int updateCoverTestGroupInterfaceById(TestGroupInterface testGroupInterface);


    int deleteTestGroupInterfaceById(@Param("id") Long id);


    List<TestGroupInterface> selectByGroupId(@Param("groupId") Long groupId);

    List<TestGroupInterface> selectByGroupIdInterFaceIds(@Param("menuId") Long menuId, @Param("interfaceIds") List<Long> interfaceIds);

    int selectMaxPositionId(@Param("groupId") Long groupId);

    void updateTestGroupInterfaceBy(@Param("groupId") Long groupId, @Param("position") Integer position);

    List<TestGroupInterface> selectByGroupIdInterFaceIdsIsDisable(@Param("groupId") Long groupId, @Param("interfaceIds") List<Long> interfaceIds, @Param("isDisable") int isDisable);

    List<TestGroupInterface> selectByGroupIdIsDisable(@Param("groupId") Long groupId, @Param("isDisable") int isDisable);

    List<TestGroupInterface> selectByMenuId(@Param("menuId") Long menuId);

    List<TestGroupInterface> selectByInterfaceId(@Param("interfaceId") Long interfaceId);

    List<TestGroupInterface> selectByGroupIdOrderByPosition(@Param("groupId") Long groupId);

    List<TestGroupInterface> selectByIds(@Param("ids") List<Long> ids);

    void updateBatch(List<TestGroupInterface> interfaces);
}