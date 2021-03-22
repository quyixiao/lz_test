package com.admin.crawler.dto.test;

import lombok.Data;

/**
 * @Author: djc
 * @Desc:
 * @Date: 2021/1/6 15:03
 */
@Data
public class SysMenuDto {
    //id
    private Long id;
    //父菜单ID，一级菜单为0
    private Long parentId;
    //空间id
    private Long spaceId;
    //菜单名称
    private String name;
    //菜单URL
    private String url;
    //类型   0：目录   1：菜单   2：按钮
    private Integer type;
    //菜单图标
    private String icon;
    //排序
    private Integer sort;
}
