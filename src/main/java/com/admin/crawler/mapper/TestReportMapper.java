package com.admin.crawler.mapper;

import com.admin.crawler.base.BaseMapper;
import com.admin.crawler.entity.TestReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试报告 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
@Mapper
public interface TestReportMapper extends BaseMapper<TestReport> {


    TestReport selectTestReportById(@Param("id") Long id);


    Long insertTestReport(TestReport testReport);


    int updateTestReportById(TestReport testReport);


    int updateCoverTestReportById(TestReport testReport);


    int deleteTestReportById(@Param("id") Long id);

    List<TestReport> selectByUniqueFlag(@Param("uniqueId") String uniqueId);

    TestReport selectLastByUniqueFlag(@Param("uniqueId") String uniqueId);

    List<TestReport> selectByUniqueFlagStatus(@Param("uniqueId") String uniqueId, @Param("statusList") List<Integer> statusList);
}