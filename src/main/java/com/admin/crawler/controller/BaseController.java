package com.admin.crawler.controller;


import com.admin.crawler.aop.LogAspect;
import com.admin.crawler.dto.test.UserInfo;

public abstract class BaseController {

    public UserInfo getUser(){
        return LogAspect.THREAD_LOCAL.get();
    }
    public Long getUserId(){
        UserInfo userInfo = LogAspect.THREAD_LOCAL.get();
        return userInfo !=null ?userInfo.getUserId():null;
    }

    public Long getSpaceId(){
        Long spaceId = LogAspect.SPACE_ID.get();
        return spaceId;
    }
}
