package com.imooc.log.stack.exception;

import lombok.AllArgsConstructor;

/**
 * <h1>自定义系统错误枚举</h1>
 * */
@AllArgsConstructor
public enum BaseErrorEnum implements BaseErrorInfo {

    SUCCESS("10000", "成功"),
    PARAM_INVALID("10001", "请求参数不合法"),
    INNER_LOGIC_ERROR("10002", "内部逻辑出错"),
    CAN_NOT_FOUND_RESOURCE("10003", "找不到对应的资源"),
    ;

    private final String errorCode;
    private final String errorMessage;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
