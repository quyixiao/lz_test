package com.admin.crawler.service.impl;

import com.admin.crawler.aop.LogAspect;
import com.admin.crawler.bo.RunDto;
import com.admin.crawler.dto.test.GroupReq;
import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.entity.TestGroup;
import com.admin.crawler.entity.TestGroupInterface;
import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.entity.TestReport;
import com.admin.crawler.mapper.TestGroupInterfaceMapper;
import com.admin.crawler.mapper.TestGroupMapper;
import com.admin.crawler.service.TestGroupInterfaceService;
import com.admin.crawler.service.TestGroupService;
import com.admin.crawler.service.TestInterfaceService;
import com.admin.crawler.service.TestReportService;
import com.admin.crawler.utils.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsh.service.ImportHelpService;
import tsh.t.TTuple1;
import tsh.t.TTuple3;
import tsh.util.Console;
import tsh.util.Utils;

import java.util.*;

/**
 * <p>
 * 测试组 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
@Slf4j
public class TestGroupServiceImpl implements TestGroupService {


    @Autowired
    private TestGroupMapper testGroupMapper;

    @Autowired
    private TestGroupInterfaceService testGroupInterfaceService;

    @Autowired
    private TestGroupInterfaceMapper testGroupInterfaceMapper;

    @Autowired
    private TestInterfaceService testInterfaceService;

    @Autowired
    private ImportHelpService importHelpService;

    @Autowired
    private TestReportService testReportService;

    @Override
    public TestGroup selectTestGroupById(Long id) {
        return testGroupMapper.selectTestGroupById(id);
    }


    @Override
    public Long insertTestGroup(TestGroup testGroup) {
        return testGroupMapper.insertTestGroup(testGroup);
    }


    @Override
    public int updateTestGroupById(TestGroup testGroup) {
        return testGroupMapper.updateTestGroupById(testGroup);
    }


    @Override
    public int updateCoverTestGroupById(TestGroup testGroup) {
        return testGroupMapper.updateCoverTestGroupById(testGroup);
    }


    @Override
    public int deleteTestGroupById(Long id) {
        return testGroupMapper.deleteTestGroupById(id);
    }

    @Override
    public R selectListBy(GroupReq req) {
        List<TestGroup> list = testGroupMapper.selectAll(LogAspect.SPACE_ID.get(), req.getMenuId());
        return R.ok().putData(list);
    }

    @Override
    public R doAddOrUpdate(GroupReq req) {
        if (req.getId() == null) {         //添加
            TestGroup group = new TestGroup();
            group.setMenuId(req.getMenuId());
            group.setName(req.getName());
            group.setSpaceId(LogAspect.SPACE_ID.get());
            UserInfo userInfo = LogAspect.THREAD_LOCAL.get();
            group.setUserId(userInfo.getUserId());
            group.setRealName(userInfo.getRealName());
            Integer maxPosition = testGroupMapper.selectMaxPositionId(LogAspect.SPACE_ID.get(), req.getMenuId());
            group.setPosition(maxPosition + 1);
            testGroupMapper.insertTestGroup(group);
        } else {
            TestGroup group = testGroupMapper.selectTestGroupById(req.getId());
            group.setName(req.getName());
            group.setIsDelete(req.getIsDelete());
            group.setIsDisable(req.getIsDisable());
            group.setPosition(req.getPosition());
            if (req.getPosition() != null && req.getPosition() > 0) {
                List<TestGroup> testGroups = testGroupMapper.selectbyMenuId(req.getMenuId());
                int flag = 0;
                for (int i = 0; i < testGroups.size(); i++) {
                    TestGroup testGroup = testGroups.get(i);
                    if (testGroup.getId().equals(group.getId())) {
                        flag = i;
                    }
                }
                testGroups.remove(flag);
                testGroups.add(req.getPosition() - 1, group);
                int i = 1;
                for (TestGroup testGroup : testGroups) {
                    testGroup.setPosition(i);
                    testGroupMapper.updateTestGroupById(testGroup);
                    i++;
                }
            }
            testGroupMapper.updateTestGroupById(group);
        }
        return R.ok();
    }


    @Override
    public R doAddInterface(GroupReq req) {
        String interfaceIds[] = req.getInterfaceIds().split(",");
        TestGroup testGroup = testGroupMapper.selectTestGroupById(req.getId());
        for (String interfaceId : interfaceIds) {
            TestGroupInterface testGroupInterface = new TestGroupInterface();
            testGroupInterface.setGroupId(testGroup.getId());
            testGroupInterface.setMenuId(testGroup.getMenuId());
            TestInterface testInterface = testInterfaceService.selectTestInterfaceById(NumberUtil.objToLong(interfaceId));
            testGroupInterface.setInterfaceId(testInterface.getId());
            testGroupInterface.setName(testInterface.getName());
            testGroupInterface.setSpaceId(testGroup.getSpaceId());
            int maxPosition = testGroupInterfaceMapper.selectMaxPositionId(testGroup.getId());
            testGroupInterface.setPosition(maxPosition + 1);
            testGroupInterfaceMapper.insertTestGroupInterface(testGroupInterface);
        }
        return R.ok();
    }

    @Override
    public R interfaceList(GroupReq req) {
        List<TestGroupInterface> testGroupInterfaces = testGroupInterfaceMapper.selectByGroupIdOrderByPosition(req.getId());
        return R.ok().putData(testGroupInterfaces);
    }

    @Override
    public R interfaceUpdate(GroupReq req) {
        if (req.getIsDelete() != null && req.getIsDelete() > 0) {
            testGroupInterfaceMapper.deleteTestGroupInterfaceById(req.getId());     //删除数据
            return R.ok();
        }
        TestGroupInterface testGroupInterface = testGroupInterfaceMapper.selectTestGroupInterfaceById(req.getId());
        testGroupInterface.setIsDisable(req.getIsDisable());
        if (req.getPosition() != null && req.getPosition() > 0) {
            List<TestGroupInterface> testGroupInterfaces = testGroupInterfaceService.selectByGroupIdOrderByPosition(testGroupInterface.getGroupId());
            int flag = 0;
            for (int i = 0; i < testGroupInterfaces.size(); i++) {
                if (testGroupInterface.getId().equals(testGroupInterfaces.get(i).getId())) {
                    flag = i;
                    break;
                }
            }
            testGroupInterfaces.remove(flag);
            testGroupInterfaces.add(req.getPosition() - 1, testGroupInterface);
            for (int i = 0; i < testGroupInterfaces.size(); i++) {
                TestGroupInterface testGroupInterface1 = testGroupInterfaces.get(i);
                testGroupInterface1.setPosition(i + 1);
                testGroupInterfaceService.updateTestGroupInterfaceById(testGroupInterface1);
            }
        }
        testGroupInterface.setPosition(req.getPosition());
        testGroupInterfaceMapper.updateTestGroupInterfaceById(testGroupInterface);
        return R.ok();
    }


    @Override
    public void groupTest(GroupReq req) {
        ImportHelpService resouceHelp = new ImportHelpServiceImpl(req.getMenuId());
        Map<String, Object> init = new LinkedHashMap<>();
        List<String> codes = SourceUtils.getFileContents("classpath*:code/**/*.tsh");
        for (String content : codes) {
            try {
                TTuple1<Map<String, Object>> baseVarible = Utils.run(content, null, null, null, resouceHelp).getData();
                init.putAll(baseVarible.getFirst());
            } catch (Exception e) {

                return;
            }
        }
        Map<String, Object> globals = new LinkedHashMap<>();
        Map<String, Object> imports = new LinkedHashMap<>();
        String testIds = req.getTestIds();
        String groupInterfaceIds[] = testIds.split("&");
        Map<Long ,List<TestGroupInterface>> testGroupInterfaceMap = new HashMap<>();
        List<Long> groupIds = new ArrayList<>();
        for (String groupInterfaceId : groupInterfaceIds) {
            String groupI[] = groupInterfaceId.split(":");
            String groupId = groupI[0];
            groupIds.add(NumberUtil.objToLong(groupId));
            List<TestGroupInterface> testGroupInterfaces = null;
            if (groupI.length > 1 && StringUtil.isNotBlank(groupI[1])) {
                testGroupInterfaces = testGroupInterfaceMapper.selectByIds(StringUtil.splitToLongList(groupI[1], ","));
            } else {
                testGroupInterfaces = testGroupInterfaceMapper.selectByGroupIdIsDisable(NumberUtil.objToLong(groupId), 0);
            }
            testGroupInterfaceMap.put(NumberUtil.objToLong(groupId),testGroupInterfaces);
        }
        List<TestGroup> testGroupList = testGroupMapper.selectByIds(groupIds);
        for (TestGroup testGroup : testGroupList) {
            UserInfo userInfo = LogAspect.THREAD_LOCAL.get();
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            TTuple3<Map<String, Object>, Map<String, Object>, Map<String, Object>> data = null;
            for (TestGroupInterface testGroupInterface : testGroupInterfaceMap.get(testGroup.getId())) {
                TestInterface testInterface = testInterfaceService.selectTestInterfaceById(testGroupInterface.getInterfaceId());
                List<RunDto> list = new ArrayList<>();
                TestReport testReport = new TestReport();
                testReport.setGroupId(NumberUtil.objToLong(testGroup.getId()));
                testReport.setInterfaceId(testInterface.getId());
                testReport.setMenuId(testInterface.getMenuId());
                testReport.setUniqueFlag(req.getUniqueId());
                testReport.setUserId(userInfo.getUserId());
                testReport.setRealName(userInfo.getRealName());
                testReport.setGroupInterfaceId(testGroupInterface.getId());
                try {
                    data = Utils.run(testInterface.getCode(), init, globals, imports, resouceHelp).getData();
                    globals = data.getSecond();
                    imports = data.getThird();
                    list.addAll(getRunInfo());
                    StringBuilder sb = new StringBuilder();
                    if (imports != null) {
                        for (Map.Entry<String, Object> entry : imports.entrySet()) {
                            sb.append(entry.getValue()).append(" ");
                        }
                    }
                    list.add(new RunDto("black", sb.toString()));
                    testReport.setExeResult(JSON.toJSONString(list));
                    testReport.setStatus(0);
                    testReport.setExeResultFlag(0);
                    testReportService.insertTestReport(testReport);
                } catch (Exception e) {
                    list.addAll(getRunInfo());
                    list.add(new RunDto("red", ExceptionUtils.dealException(e)));
                    testReport.setExeResult(JSON.toJSONString(list));
                    testReport.setStatus(3);
                    testReport.setExeResultFlag(1);
                    testReportService.insertTestReport(testReport);
                    return;
                } catch (Error e) {
                    list.addAll(getRunInfo());
                    list.add(new RunDto("red", ExceptionUtils.dealException(e)));
                    testReport.setExeResult(JSON.toJSONString(list));
                    testReport.setStatus(3);
                    testReport.setExeResultFlag(1);
                    testReportService.insertTestReport(testReport);
                    return;
                }
            }
        }
        TestReport t = testReportService.selectLastByUniqueFlag(req.getUniqueId());
        if (t != null && t.getStatus() != 3) {
            t.setStatus(2);
            log.info(t.getId() + " set status to 2 ");
            testReportService.updateTestReportById(t);
        }
    }

    @Override
    public List<TestGroup> selectByIds(List<Long> ids) {
        return testGroupMapper.selectByIds(ids);
    }


    public static List<RunDto> getRunInfo() {
        List<String> list = Console.infoList();
        List<RunDto> result = new ArrayList<>();
        if (list != null) {
            for (String l : list) {
                if (isException(l)) {
                    result.add(new RunDto("red", l));
                } else {
                    result.add(new RunDto("black", l));
                }
            }
        }
        return result;
    }


    public static boolean isException(String message) {
        String s[] = message.split("\n");
        if(s !=null && s.length > 0){
            for (String l : s) {
                if (StringUtil.isNotBlank(l)) {
                    if (l.contains("Exception")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }


}
