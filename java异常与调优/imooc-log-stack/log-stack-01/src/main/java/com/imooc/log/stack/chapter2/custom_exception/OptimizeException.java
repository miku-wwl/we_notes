package com.imooc.log.stack.chapter2.custom_exception;

import lombok.Data;

/**
 * <h1>用户性能优化的自定义异常</h1>
 * */
public class OptimizeException {

    @Data
    public static class CustomException extends RuntimeException {

        private String key;
        private String message;

        public CustomException(String key, String message) {
            super(String.format("(%s)-[%s]", key , message));
            this.key = key;
            this.message = message;
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }

        @Override
        public String toString() {
            return String.format("(%s)-[%s]", key , message);
        }
    }

    private static void fun(String name) {
        if (!"qinyi".equals(name)) {
            throw new CustomException("OptimizeException.fun.35", "name is not qinyi...");
        }
    }

    public static void main(String[] args) {

        fun("imooc-qinyi");
    }
}
