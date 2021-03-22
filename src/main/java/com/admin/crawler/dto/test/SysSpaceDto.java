package com.admin.crawler.dto.test;

import lombok.Data;

/**
 * @Author: djc
 * @Desc:
 * @Date: 2021/1/6 14:40
 */
@Data
public class SysSpaceDto {
    //id
    private Long id;
    //空间名称
    private String name;
    //排序
    private Integer sort;
    //gitLab 地址
    private String gitlabUrl;
}
