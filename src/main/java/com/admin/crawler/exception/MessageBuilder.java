package com.admin.crawler.exception;

import com.admin.crawler.utils.StringUtil;

public class MessageBuilder {

    public static String buildMessage(String errorCode, String errorMsg, String code, String msg) {
        StringBuilder message = new StringBuilder();
        if (StringUtil.isNotBlank(errorMsg)) {
            message.append(errorMsg);
        }
        if (StringUtil.isNotBlank(errorCode)) {
            message.append("(");
            message.append(errorCode);
            message.append(")");
        }
        message.append(" ; Response : ");
        message.append(msg);
        message.append("(");
        message.append(code);
        message.append(")");
        return message.toString();
    }

}
