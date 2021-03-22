package com.admin.crawler.dto.test;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;

    private String code;

    private String token ;
}
