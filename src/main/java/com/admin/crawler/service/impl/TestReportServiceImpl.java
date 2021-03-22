package com.admin.crawler.service.impl;

import com.admin.crawler.entity.TestReport;
import com.admin.crawler.mapper.TestReportMapper;
import com.admin.crawler.service.TestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试报告 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
public class TestReportServiceImpl implements TestReportService {


    @Autowired
    private TestReportMapper testReportMapper;


    @Override
    public TestReport selectTestReportById(Long id) {
        return testReportMapper.selectTestReportById(id);
    }


    @Override
    public Long insertTestReport(TestReport testReport) {
        return testReportMapper.insertTestReport(testReport);
    }


    @Override
    public int updateTestReportById(TestReport testReport) {
        return testReportMapper.updateTestReportById(testReport);
    }


    @Override
    public int updateCoverTestReportById(TestReport testReport) {
        return testReportMapper.updateCoverTestReportById(testReport);
    }


    @Override
    public int deleteTestReportById(Long id) {
        return testReportMapper.deleteTestReportById(id);
    }

    @Override
    public List<TestReport> selectByUniqueFlag(String uniqueId) {
        return testReportMapper.selectByUniqueFlag(uniqueId);
    }

    @Override
    public TestReport selectLastByUniqueFlag(String uniqueId) {
        return testReportMapper.selectLastByUniqueFlag(uniqueId);
    }

    @Override
    public List<TestReport> selectByUniqueFlagStatus(String uniqueId, List<Integer> statusList) {
        return testReportMapper.selectByUniqueFlagStatus(uniqueId, statusList);
    }


}
