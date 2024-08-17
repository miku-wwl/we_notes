package com.imooc.log.stack.chapter2.custom_exception;

/**
 * <h1>为业务/框架定义的专用异常</h1>
 * */
public class ProfessionalException {

    /**
     * <h2>自定义异常的基类</h2>
     * */
    public static class BizException extends RuntimeException {

        // 定义错误码, 还可以继续扩展定义各种属性
        private String errorCode;

        public BizException(String errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }
    }

    /**
     * <h2>商品模块异常类: 查看商品</h2>
     * */
    public static class ViewGoodsException extends BizException {

        public ViewGoodsException(String errorCode, String message) {
            super(errorCode, message);
        }
    }
}
