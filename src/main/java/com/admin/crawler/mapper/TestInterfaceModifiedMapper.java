package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.TestInterfaceModified;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 接口修改记录表 服务类
* </p>
*
* @author ququququ
* @since 2021-03-09
*/
@Mapper
public interface TestInterfaceModifiedMapper extends BaseMapper<TestInterfaceModified> {


	TestInterfaceModified selectTestInterfaceModifiedById(@Param("id") Long id);


	Long insertTestInterfaceModified(TestInterfaceModified testInterfaceModified);


	int updateTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified);


	int updateCoverTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified);


	int deleteTestInterfaceModifiedById(@Param("id") Long id);


}