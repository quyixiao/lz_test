package com.admin.crawler.controller;


import com.admin.crawler.entity.TestUser;
import com.admin.crawler.entity.UserInfoTest;
import com.admin.crawler.mapper.TestUserMapper;
import com.alibaba.fastjson.JSON;
import com.lz.mybatis.plugin.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestUserController {

    @Autowired
    private TestUserMapper testUserMapper;

    @RequestMapping("/selectTestUserById")
    public String test() {
        TestUser processUser = testUserMapper.selectTestUserById(14l);
        System.out.println(processUser);
        return "测试成功";
    }


    @RequestMapping("/selectByUserNameMobile")
    public String selectByUserNameMobile() {
        List<TestUser> processUser = testUserMapper.selectByUserNameMobile("zhangsan", "184581149");

        List<TestUser> processUserxx = testUserMapper.selectByUserNameMobile1("zhangsan", "184581149");
        System.out.println(processUser);
        return "测试成功";
    }

    @RequestMapping("/selectUserByCondition")
    public String selectUserByCondition() {
        UserInfoTest userInfo = new UserInfoTest();
        userInfo.setStaffId(10l);
        userInfo.setUsername("184xxxx");
        TestUser testUser = testUserMapper.selectUserByCondition(1l, 1, "张三", userInfo);
        System.out.println(testUser);
        return "测试成功";
    }


    @RequestMapping("/selectUserByRealName")
    public String selectUserByRealName() {
        List<TestUser> testUser = testUserMapper.selectUserByRealName("张三", "184");
        System.out.println(testUser);
        return "测试成功";
    }

    @RequestMapping("/selectUserBy")
    public String selectUserBy() {
        List<TestUser> testUser = testUserMapper.selectUserBy("张三", "184", "desc");
        System.out.println(testUser);
        return "测试成功";
    }


    @RequestMapping("/selectByTaskId")
    public String selectByTaskId() {
        List<TestUser> testUser = testUserMapper.selectByTaskId(null, null);
        System.out.println(testUser);
        return "测试成功";
    }

    @RequestMapping("/selectByTaskRealNameMobile")
    public String selectByTaskRealNameMobile() {
        List<TestUser> testUser = testUserMapper.selectByTaskRealNameMobile(null, null);
        System.out.println(testUser);
        return "测试成功";
    }


    @RequestMapping("/countUser")
    public String countUser() {
        int a = testUserMapper.countUser("张");
        System.out.println(a);
        return "测试成功";
    }


    @RequestMapping("/testInsert")
    public String testInsert() {
        TestUser testUser = new TestUser();
        testUser.setBranchId(10l);
        testUser.setMobile("184xxxx");
        testUser.setRealName("张三");
        testUser.setStaffId(10l);
        testUser.setUsername("zhangsan");
        testUserMapper.insertTestUser(testUser);
        System.out.println(testUser);
        return "测试成功";
    }


    @RequestMapping("/insertTestUserBatch")
    public String insertBatchTestUser() {
        TestUser testUser = new TestUser();

        testUser.setBranchId(10l);
        testUser.setMobile("184xxxx");
        testUser.setRealName("张三");
        testUser.setStaffId(10l);
        testUser.setUsername("zhangsan");

        TestUser testUser2 = new TestUser();
        testUser2.setBranchId(10l);
        testUser2.setMobile("18258136007");
        testUser2.setRealName("张三");
        testUser2.setStaffId(10l);
        testUser2.setUsername("zhangsan");
        List<TestUser> testUsers = new ArrayList<>();
        testUsers.add(testUser);
        testUsers.add(testUser2);

        testUserMapper.insertBatchTestUser(testUsers);
        for (TestUser testUser1 : testUsers) {
            System.out.println(testUser1);
        }
        return "测试成功";
    }


    @RequestMapping("/insertTestUserBatchByArray")
    public String insertTestUserBatchByArray() {
        TestUser testUser = new TestUser();

        testUser.setBranchId(10l);
        testUser.setMobile("184xxxx");
        testUser.setRealName("张三");
        testUser.setStaffId(10l);
        testUser.setUsername("zhangsan");

        TestUser testUser2 = new TestUser();
        testUser2.setBranchId(10l);
        testUser2.setMobile("18258136007");
        testUser2.setRealName("张三");
        testUser2.setStaffId(10l);
        testUser2.setUsername("zhangsan");
        TestUser[] testUsers = new TestUser[2];
        testUsers[0] = testUser;
        testUsers[1] = testUser2;

        testUserMapper.insertTestUserBatch(testUsers);
        for (TestUser testUser1 : testUsers) {
            System.out.println(testUser1);
        }
        return "测试成功";
    }

    @RequestMapping("/testUpdate1")
    public String testUpdate1() {
        TestUser testUser = new TestUser();
        testUser.setBranchId(10l);
        testUser.setMobile("184xxxx");
        testUser.setRealName("张三xxxxxxx");
        testUser.setStaffId(10l);
        testUser.setUsername("zhangsan");
        testUser.setId(51l);
        testUserMapper.updateTestUserById(testUser);
        System.out.println(testUser);
        return "测试成功";
    }

    @RequestMapping("/updateTestUserUserNamePassword")
    public String updateTestUserUserNamePassword() {
        testUserMapper.updateTestUserUserNamePassword("张", "123", 51l, 10l);
        return "测试成功";
    }


    @RequestMapping("/updateBatchList")
    public String updateBatchList() {
        List<TestUser> testUsers = testUserMapper.selectAll();
        for (TestUser testUser : testUsers) {
            //testUser.setUsername("ququququ");
            testUser.setTaskId(23l);
        }
        testUserMapper.updateBatchTestUser(testUsers);
        return "测试成功";
    }


    @RequestMapping("/updateBatchArray")
    public String updateBatchArray() {
        List<TestUser> testUsers = testUserMapper.selectAll();
        long i = 0l;
        for (TestUser testUser : testUsers) {
            testUser.setUsername("xxxxxx");
            testUser.setTaskId(i++);
            testUser.setType((int) i);
        }
        testUserMapper.updateBatchArray(testUsers.toArray(new TestUser[testUsers.size()]));
        return "测试成功";
    }


    @RequestMapping("/updateRealNameById")
    public String updateRealNameById() {
        testUserMapper.updateRealNameById("张三aaa", 50l);
        return "测试成功";
    }

    @RequestMapping("/deleteTestUserById")
    public String deleteTestUserById() {
        testUserMapper.deleteTestUserById(50l);
        return "测试成功";
    }


    @RequestMapping("/deleteTestUserByIds")
    public String deleteTestUserByIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(43l);
        ids.add(44l);
        testUserMapper.deleteTestUserByIds(ids);
        return "测试成功";
    }


    @RequestMapping("/deleteBatch")
    public String deleteBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(43l);
        ids.add(44l);
        testUserMapper.deleteBatch();
        return "测试成功";
    }


    @RequestMapping("/selectPage")
    public String selectPage(int currPage, int pageSize) {
        Page page = testUserMapper.selectPage("张三", "184xxxx", currPage, pageSize);
        System.out.println(JSON.toJSONString(page));
        return JSON.toJSONString(page);
    }

}
