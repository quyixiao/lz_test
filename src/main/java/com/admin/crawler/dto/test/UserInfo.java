package com.admin.crawler.dto.test;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String mobile;
    private String realName;
    private String token;
    private Long userId;

}
