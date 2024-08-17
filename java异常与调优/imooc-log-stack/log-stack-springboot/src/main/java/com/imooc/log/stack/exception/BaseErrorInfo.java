package com.imooc.log.stack.exception;

/**
 * <h1>错误描述接口</h1>
 * */
public interface BaseErrorInfo {

    /**
     * <h2>返回错误码</h2>
     * */
    String getErrorCode();

    /**
     * <h2>返回错误信息</h2>
     * */
    String getErrorMessage();
}
