package com.admin.crawler.exception;

/**
 * Micro Services Define
 * 服务编码定义
 *
 * @author tom
 * @version 1.0
 * @date 2017-06-07 10:56
 */
public enum MSDEnum {
    // base-platform
    LZBP_REGISTER("101", "服务注册中心"),
    LZBP_WEBHUB("102", "WEBHUB"),

    // finance
    LZFN_SBP("200", "MY钱包-API"),

    NOT_SPECIFIED("999", "NotSpecified");
    public final String SERVICE_ID;
    public final String SERVICE_DESC;

    private MSDEnum(String SERVICE_ID, String SERVICE_DESC) {
        this.SERVICE_ID = SERVICE_ID;
        this.SERVICE_DESC = SERVICE_DESC;
    }
}
