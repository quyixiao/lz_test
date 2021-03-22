package com.admin.crawler.entity;


import com.lz.mybatis.plugin.annotations.AND;
import com.lz.mybatis.plugin.annotations.EQ;
import com.lz.mybatis.plugin.annotations.LIKE;
import com.lz.mybatis.plugin.annotations.OR;

@OR
public class UserInfoTest {

    @LIKE
    @AND
    private String username;
    @EQ
    @OR
    private Long staffId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}
