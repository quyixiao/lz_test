package com.admin.crawler.controller;

import com.admin.crawler.bo.RunDto;
import com.admin.crawler.dto.test.InterfaceReq;
import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.service.TestInterfaceService;
import com.admin.crawler.service.impl.TestGroupServiceImpl;
import com.admin.crawler.utils.ExceptionUtils;
import com.admin.crawler.utils.R;
import com.admin.crawler.utils.SourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tsh.service.impl.ResouceHelp;
import tsh.t.TTuple1;
import tsh.t.TTuple3;
import tsh.util.Console;
import tsh.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interface")
@Slf4j
public class InterfaceController {

    @Autowired
    private TestInterfaceService testInterfaceService;

    @RequestMapping("/list")
    public R list(@RequestBody InterfaceReq req) {
        GroupController.save();
        return testInterfaceService.selectListBy(req);
    }

    @RequestMapping("/doAddOrUpdate")
    public R doAddOrUpdate(@RequestBody InterfaceReq req) {
        GroupController.save();
        return testInterfaceService.doAddOrUpdate(req);
    }

    @RequestMapping("/getGroupInfo")
    public R getGroupInfo(@RequestBody InterfaceReq req) {
        GroupController.save();
        return testInterfaceService.getGroupInfo(req);
    }

    @RequestMapping("/toUpate")
    public R toUpate(@RequestBody InterfaceReq req) {
        GroupController.save();
        TestInterface group = testInterfaceService.selectTestInterfaceById(req.getId());
        return R.ok().putData(group);
    }

    @RequestMapping("/run")
    public R run(@RequestBody InterfaceReq req) throws Exception {
        List<RunDto> list = new ArrayList<>();
        try {
            ResouceHelp resouceHelp = new ResouceHelp();
            Map<String, Object> init = new LinkedHashMap<>();
            List<String> codes = SourceUtils.getFileContents("classpath*:code/**/*.tsh");
            for (String content : codes) {
                TTuple1<Map<String, Object>> baseVarible = Utils.run(content, null, null, null, resouceHelp).getData();
                init.putAll(baseVarible.getFirst());
            }
            Map<String, Object> globals = new LinkedHashMap<>();
            Map<String, Object> imports = new LinkedHashMap<>();
            TTuple3<Map<String, Object>, Map<String, Object>, Map<String, Object>> data = null;
            if(req.getId() !=null){
                TestInterface testInterface = testInterfaceService.selectTestInterfaceById(req.getId());
                req.setCode(testInterface.getCode());
            }
            data = Utils.run(req.getCode(), init, globals, imports, resouceHelp).getData();
            imports = data.getThird();
            list.addAll(TestGroupServiceImpl.getRunInfo());
            StringBuilder sb = new StringBuilder();
            if (imports != null) {
                for (Map.Entry<String, Object> entry : imports.entrySet()) {
                    sb.append(entry.getValue()).append(" ");
                }
            }
            list.add(new RunDto("black", sb.toString()));
            return R.ok().putData(list);
        } catch (Exception e) {
            list.addAll(TestGroupServiceImpl.getRunInfo());
            list.add(new RunDto("red", ExceptionUtils.dealException(e)));
            log.error("执行异常", e);
            return R.error("执行异常").putData(list);
        }
    }


}
