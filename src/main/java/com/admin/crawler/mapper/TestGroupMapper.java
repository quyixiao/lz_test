package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.TestGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 测试组 服务类
* </p>
*
* @author ququququ
* @since 2021-03-09
*/
@Mapper
public interface TestGroupMapper extends BaseMapper<TestGroup> {


	TestGroup selectTestGroupById(@Param("id") Long id);


	Long insertTestGroup(TestGroup testGroup);


	int updateTestGroupById(TestGroup testGroup);


	int updateCoverTestGroupById(TestGroup testGroup);


	int deleteTestGroupById(@Param("id") Long id);


	List<TestGroup> selectAll(@Param("spaceId") Long spaceId, @Param("menuId") Long menuId);

	Integer selectMaxPositionId(@Param("spaceId") Long spaceId, @Param("menuId") Long menuId);

	void updateTestGroupSpaceIdMenuId(@Param("spaceId") Long spaceId, @Param("menuId") Long menuId, @Param("position") Integer position);

    List<TestGroup> selectByIds(@Param("ids") List<Long> ids);

	List<TestGroup> selectbyMenuId(@Param("menuId") Long menuId);
}