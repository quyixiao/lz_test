package com.admin.crawler.bo;

import com.admin.crawler.entity.TestInterface;
import lombok.Data;

@Data
public class TestInterfaceDto extends TestInterface {

    //被所有的组引用次数
    private int groupRefreshNum = 0 ;

    // 被当前组引用次数
    private int curGroupRefreshNum = 0 ;

}
