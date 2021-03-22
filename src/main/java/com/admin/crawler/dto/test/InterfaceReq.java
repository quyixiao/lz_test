package com.admin.crawler.dto.test;

import lombok.Data;

@Data
public class InterfaceReq {
    private Long id ;
    private Long menuId;

    //接口名称
    private String name;
    //代码
    private String code;
    //文件名称，用户其他接口 import
    private String fileName;

    private Integer isDelete;
    //组 id
    private Long groupId;

    //
    private Long changePositionId;

}
