package com.imooc.log.stack.chapter2.complete_exception;

/**
 * <h1>想办法打印完整的异常栈信息</h1>
 * */
public class CompleteException {

    private void imooc1() throws Exception {
        throw new Exception("imooc1 has exception...");
    }

    private void imooc2() throws Exception {

        try {
            imooc1();
        } catch (Exception ex) {
            throw new Exception("imooc2 has exception...", ex);
        }
    }

    private void imooc3() {
        try {
            imooc2();
        } catch (Exception ex) {
            throw new RuntimeException("imooc3 has exception...", ex);
        }
    }

    public static void main(String[] args) {

//        try {
//            new CompleteException().imooc3();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        new CompleteException().imooc3();
    }
}
