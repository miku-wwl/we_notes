package com.imooc.log.stack.advice;

import com.imooc.log.stack.exception.BizException;
import com.imooc.log.stack.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>全局异常处理</h1>
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * <h2>捕获 BizException 进行统一的异常处理</h2>
     * */
    @ExceptionHandler(value = BizException.class)
    public CommonResponse<Void> bizExceptionHandler(HttpServletRequest req,
                                                    BizException ex) {
        log.error("has some error in request: [{}], errorCode: [{}], errorMsg: [{}]",
                req.getRequestURI(), ex.getErrorCode(), ex.getErrorMessage());
        return new CommonResponse<>(Integer.valueOf(ex.getErrorCode()), ex.getErrorMessage());
    }

    /**
     * <h2>兜底异常捕获</h2>
     * */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<String> handlerException(HttpServletRequest req, Exception ex) {

        log.error("has exception in request: [{}]", req.getRequestURI(), ex);
        CommonResponse<String> response = new CommonResponse<>(-1,
                "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
