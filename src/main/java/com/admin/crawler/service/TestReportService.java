package com.admin.crawler.service;

import com.admin.crawler.entity.TestReport;

import java.util.List;

/**
 * <p>
 * 测试报告 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
public interface TestReportService {


    TestReport selectTestReportById(Long id);


    Long insertTestReport(TestReport testReport);


    int updateTestReportById(TestReport testReport);


    int updateCoverTestReportById(TestReport testReport);


    int deleteTestReportById(Long id);


    List<TestReport> selectByUniqueFlag(String uniqueId);

    TestReport selectLastByUniqueFlag(String uniqueId);

    List<TestReport> selectByUniqueFlagStatus(String uniqueId, List<Integer> asList);
}