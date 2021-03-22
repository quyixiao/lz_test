package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.TestInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 接口 服务类
* </p>
*
* @author ququququ
* @since 2021-03-09
*/
@Mapper
public interface TestInterfaceMapper extends BaseMapper<TestInterface> {


	TestInterface selectTestInterfaceById(@Param("id") Long id);


	Long insertTestInterface(TestInterface testInterface);


	int updateTestInterfaceById(TestInterface testInterface);


	int updateCoverTestInterfaceById(TestInterface testInterface);


	int deleteTestInterfaceById(@Param("id") Long id);


    List<TestInterface> selectAll(@Param("menuId") Long menuId, @Param("name") String name);

	Integer selectMaxPosition(@Param("menuId") Long menuId);

	void updateTestInterfaceBySpaceIdMenuId(@Param("spaceId") Long spaceId, @Param("menuId") Long menuId, @Param("position") Integer position);

	TestInterface selectBySpaceIdMenuIdFileName(@Param("menuId") Long menuId, @Param("fileName") String fileName);

    List<TestInterface> selectByIds(@Param("ids") List<Long> ids);
}