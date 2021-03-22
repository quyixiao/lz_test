package com.admin.crawler.service.impl;

import com.admin.crawler.aop.LogAspect;
import com.admin.crawler.bo.TestInterfaceDto;
import com.admin.crawler.dto.test.InterfaceReq;
import com.admin.crawler.dto.test.UserInfo;
import com.admin.crawler.entity.TestGroup;
import com.admin.crawler.entity.TestGroupInterface;
import com.admin.crawler.entity.TestInterface;
import com.admin.crawler.entity.TestInterfaceModified;
import com.admin.crawler.mapper.TestGroupInterfaceMapper;
import com.admin.crawler.mapper.TestInterfaceMapper;
import com.admin.crawler.service.TestGroupInterfaceService;
import com.admin.crawler.service.TestGroupService;
import com.admin.crawler.service.TestInterfaceModifiedService;
import com.admin.crawler.service.TestInterfaceService;
import com.admin.crawler.utils.NumberUtil;
import com.admin.crawler.utils.R;
import com.admin.crawler.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 接口 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */

@Service
public class TestInterfaceServiceImpl implements TestInterfaceService {


    @Autowired
    private TestInterfaceMapper testInterfaceMapper;


    @Autowired
    private TestInterfaceModifiedService testInterfaceModifiedService;

    @Autowired
    private TestGroupInterfaceService testGroupInterfaceService;

    @Autowired
    private TestGroupService testGroupService;

    @Autowired
    private TestGroupInterfaceMapper testGroupInterfaceMapper;

    @Override
    public TestInterface selectTestInterfaceById(Long id) {
        return testInterfaceMapper.selectTestInterfaceById(id);
    }


    @Override
    public Long insertTestInterface(TestInterface testInterface) {
        return testInterfaceMapper.insertTestInterface(testInterface);
    }


    @Override
    public int updateTestInterfaceById(TestInterface testInterface) {
        return testInterfaceMapper.updateTestInterfaceById(testInterface);
    }


    @Override
    public int updateCoverTestInterfaceById(TestInterface testInterface) {
        return testInterfaceMapper.updateCoverTestInterfaceById(testInterface);
    }


    @Override
    public int deleteTestInterfaceById(Long id) {
        return testInterfaceMapper.deleteTestInterfaceById(id);
    }

    @Override
    public R selectListBy(InterfaceReq req) {
        List<TestInterface> list = testInterfaceMapper.selectAll(req.getMenuId(), req.getName());
        List<TestInterfaceDto> testInterfaceDtos = new ArrayList<>();
        List<TestGroupInterface> testGroupInterfaceList = testGroupInterfaceService.selectByMenuId(req.getMenuId());

        Map<Long, Set<Long>> map = new HashMap<>();
        for (TestGroupInterface testGroupInterface : testGroupInterfaceList) {
            Set<Long> set = map.get(testGroupInterface.getInterfaceId());
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(NumberUtil.objToLong(testGroupInterface.getGroupId()));
            map.put(testGroupInterface.getInterfaceId(), set);
        }
        Map<Long, Integer> integerMap = new HashMap<>();
        for (TestGroupInterface testGroupInterface : testGroupInterfaceList) {
            if (testGroupInterface.getGroupId().equals(req.getGroupId())) {
                Integer num = integerMap.get(testGroupInterface.getInterfaceId());
                if (num == null) {
                    num = 0;
                }
                integerMap.put(testGroupInterface.getInterfaceId(), num + 1);
            }
        }
        for (TestInterface testInterface : list) {
            TestInterfaceDto testInterfaceDto = new TestInterfaceDto();
            BeanUtils.copyProperties(testInterface,testInterfaceDto);
            Set set = map.get(testInterface.getId());
            if (set != null) {
                testInterfaceDto.setGroupRefreshNum(set.size());
            }
            testInterfaceDto.setCode(null);
            testInterfaceDto.setCurGroupRefreshNum(NumberUtil.objToIntDefault(integerMap.get(testInterface.getId()), 0));
            testInterfaceDtos.add(testInterfaceDto);
        }
        return R.ok().putData(testInterfaceDtos);
    }

    @Override
    public R doAddOrUpdate(InterfaceReq req) {

        UserInfo userInfo = LogAspect.THREAD_LOCAL.get();
        TestInterface testInterface = null;
        if (req.getId() != null) {
            testInterface = testInterfaceMapper.selectTestInterfaceById(req.getId());
            testInterface.setCode(req.getCode());
            testInterface.setFileName(req.getFileName());
            testInterface.setName(req.getName());
            if (StringUtil.isNotBlank(req.getFileName())) {
                TestInterface t = testInterfaceMapper.selectBySpaceIdMenuIdFileName(req.getMenuId(), req.getFileName());
                if (t != null) {
                    return R.error("文件名己经存在");
                }
            }
            testInterface.setFileName(req.getFileName());
            testInterface.setIsDelete(req.getIsDelete());

            testInterfaceMapper.updateTestInterfaceById(testInterface);

            List<TestGroupInterface> interfaces = testGroupInterfaceMapper.selectByInterfaceId(testInterface.getId());
            if (interfaces != null && interfaces.size() > 0) {
                for (TestGroupInterface testGroupInterface : interfaces) {
                    testGroupInterface.setName(testInterface.getName());
                }
                testGroupInterfaceMapper.updateBatch(interfaces);
            }
        } else {
            testInterface = new TestInterface();
            testInterface.setCode(req.getCode());
            testInterface.setFileName(req.getFileName());
            testInterface.setName(req.getName());
            Integer max = testInterfaceMapper.selectMaxPosition(req.getMenuId());
            testInterface.setPosition(max + 1);
            testInterface.setRealName(userInfo.getRealName());
            testInterface.setMenuId(req.getMenuId());
            testInterface.setSpaceId(LogAspect.SPACE_ID.get());
            if (StringUtil.isNotBlank(req.getFileName())) {
                TestInterface t = testInterfaceMapper.selectBySpaceIdMenuIdFileName(req.getMenuId(), req.getFileName());
                if (t != null) {
                    return R.error("文件名己经存在");
                }
            }
            testInterface.setFileName(req.getFileName());
            testInterface.setUserId(userInfo.getUserId());
            testInterfaceMapper.insertTestInterface(testInterface);

        }

        TestInterfaceModified testInterfaceModified = new TestInterfaceModified();      //保存接口修改记录
        testInterfaceModified.setCode(testInterface.getCode());
        testInterfaceModified.setRealName(userInfo.getRealName());
        testInterfaceModified.setUserId(userInfo.getUserId());
        testInterfaceModifiedService.insertTestInterfaceModified(testInterfaceModified);
        return R.ok();
    }

    @Override
    public TestInterface selectBySpaceIdMenuIdFileName(Long menuId, String fileName) {
        return testInterfaceMapper.selectBySpaceIdMenuIdFileName(menuId, fileName);
    }

    @Override
    public List<TestInterface> selectByIds(List<Long> ids) {

        return testInterfaceMapper.selectByIds(ids);
    }


    @Override
    public R getGroupInfo(InterfaceReq req) {
        List<TestGroupInterface> testGroupInterfaces = testGroupInterfaceService.selectByInterfaceId(req.getId());
        List<TestGroup> groupList = new ArrayList<>();
        if (testGroupInterfaces != null && testGroupInterfaces.size() > 0) {
            List<Long> list = new ArrayList<>();
            for (TestGroupInterface testGroupInterface : testGroupInterfaces) {
                list.add(testGroupInterface.getGroupId());
            }
            groupList = testGroupService.selectByIds(list);
        }

        return R.ok().put("data", groupList);
    }


}
