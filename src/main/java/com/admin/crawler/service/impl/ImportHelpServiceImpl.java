package com.admin.crawler.service.impl;

import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.service.TestInterfaceService;
import com.admin.crawler.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tsh.service.ImportHelpService;

@Service
public class ImportHelpServiceImpl implements ImportHelpService {

    private Long menuId;


    public ImportHelpServiceImpl() {
    }

    public ImportHelpServiceImpl(Long menuId) {
        this.menuId = menuId;
    }

    @Autowired
    private TestInterfaceService testInterfaceService;

    @Override
    public String getCode(String path) throws Exception {
        testInterfaceService = SpringContextUtils.getBean(TestInterfaceService.class);
        TestInterface testInterface = testInterfaceService.selectBySpaceIdMenuIdFileName( menuId, path);
        if(testInterface == null){
            throw  new Exception("import 文件不存在");
        }
        return testInterface.getCode();
    }
}
