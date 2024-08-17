package com.imooc.log.stack.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <h1>自定义异常类</h1>
 * */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

    private String errorCode;
    private String errorMessage;

    public BizException() {
        super();
    }

    public BizException(BaseErrorInfo errorInfo) {
        super(errorInfo.getErrorCode());
        this.errorCode = errorInfo.getErrorCode();
        this.errorMessage = errorInfo.getErrorMessage();
    }

    public BizException(BaseErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.getErrorCode(), cause);
        this.errorCode = errorInfo.getErrorCode();
        this.errorMessage = errorInfo.getErrorMessage();
    }
}
