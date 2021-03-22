package com.admin.crawler.dto.test;

import lombok.Data;

@Data
public class GroupReq {
    private Long id ;
    private Long menuId;
    private String name;
    //位置
    private Integer position;
    //是否可用，0 启用，1 禁用
    private Integer isDisable;
    //删除
    private Integer isDelete;

    private String interfaceIds;

    private String testIds;

    private String uniqueId;

    //是否增量
    private int flag ;

    private Long changePositionId ;
}
