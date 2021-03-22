package com.admin.crawler.dto.test;

import lombok.Data;

@Data
public class LoginResp {
    private Long spaceId;
    private String spaceName;
    private String userName;
    private Long userId;
    private String token;
}
