package com.admin.crawler.service;

import com.admin.crawler.entity.TestInterfaceModified;

/**
 * <p>
 * 接口修改记录表 服务类
 * </p>
 *
 * @author ququququ
 * @since 2021-03-09
 */
public interface TestInterfaceModifiedService {


    TestInterfaceModified selectTestInterfaceModifiedById(Long id);


    Long insertTestInterfaceModified(TestInterfaceModified testInterfaceModified);


    int updateTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified);


    int updateCoverTestInterfaceModifiedById(TestInterfaceModified testInterfaceModified);


    int deleteTestInterfaceModifiedById(Long id);


}