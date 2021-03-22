package com.admin.crawler.controller;


import com.admin.crawler.aop.LogAspect;
import com.admin.crawler.dto.test.GroupReq;
import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.entity.TestGroup;
import com.admin.crawler.entity.TestReport;
import com.admin.crawler.service.TestGroupService;
import com.admin.crawler.service.TestReportService;
import com.admin.crawler.utils.OrderUtil;
import com.admin.crawler.utils.R;
import com.admin.crawler.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private TestGroupService testGroupService;

    @Autowired
    private TestReportService testReportService;

    @RequestMapping("/list")
    public R list(@RequestBody GroupReq req) {
        save();
        return testGroupService.selectListBy(req);
    }


    @RequestMapping("/doAddOrUpdate")
    public R doAddOrUpdate(@RequestBody GroupReq req) {
        save();
        return testGroupService.doAddOrUpdate(req);
    }

    @RequestMapping("/toUpate")
    public R toUpate(@RequestBody GroupReq req) {
        save();
        TestGroup group = testGroupService.selectTestGroupById(req.getId());
        return R.ok().putData(group);
    }

    @RequestMapping("/doAddInterface")
    public R doAddInterface(@RequestBody GroupReq req) {
        save();
        return testGroupService.doAddInterface(req);
    }


    @RequestMapping("/interface/list")
    public R interfaceList(@RequestBody GroupReq req) {
        save();
        return testGroupService.interfaceList(req);
    }


    @RequestMapping("/interface/update")
    public R interfaceUpdate(@RequestBody GroupReq req) {
        save();
        return testGroupService.interfaceUpdate(req);
    }

    public static void save() {
        if (LogAspect.SPACE_ID.get() == null) {
            LogAspect.SPACE_ID.set(1l);
        }
        if (LogAspect.THREAD_LOCAL.get() == null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(408l);
            userInfo.setRealName("瞿贻晓");
            LogAspect.THREAD_LOCAL.set(userInfo);
        }
    }


    @RequestMapping("/groupInterface/test")
    public R groupInterfaceTest(@RequestBody GroupReq req) {
        //ids =  1:1,3,3&2:&3:4,5,6
        req.setMenuId(65l);
        if (StringUtil.isBlank(req.getTestIds())) {
            return R.error("请输入 testIds");
        }
        save();
        req.setUniqueId(OrderUtil.getUserPoolOrder(LogAspect.THREAD_LOCAL.get().getUserId() + ""));
        new Thread(new Runnable() {
            @Override
            public void run() {
                testGroupService.groupTest(req);
            }
        }).start();
        return R.ok().putData("uniqueId", req.getUniqueId());
    }


    @RequestMapping("/test/result")
    public R testResult(@RequestBody GroupReq req) {
        List<TestReport> list = new ArrayList<>();
        int flag = 0;
        int exeResultFlag = 0;
        if (req.getFlag() == 1) {
            list = testReportService.selectByUniqueFlag(req.getUniqueId());
        } else {
            list = testReportService.selectByUniqueFlagStatus(req.getUniqueId(), Arrays.asList(new Integer[]{0, 2, 3}));
            for (TestReport testReport : list) {
                if (testReport.getStatus() == 2 || testReport.getStatus() == 3) {
                    flag = 1;
                } else {
                    testReport.setStatus(1);
                    log.info(testReport.getId() + " set status to 1 ");
                    testReportService.updateTestReportById(testReport);
                }
            }
        }

        for (TestReport testReport : list) {
            exeResultFlag = new Integer(1).equals(testReport.getExeResultFlag()) ? 1 : 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("flag", flag);
        map.put("exeResultFlag", exeResultFlag);
        return R.ok().put("data", map);
    }


}
