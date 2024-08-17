package com.imooc.log.stack.chapter2.complete_exception;

/**
 * <h1>异常捕捉</h1>
 * */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        StackTraceElement[] ses = e.getStackTrace();
        System.err.println("Exception in thread \"" + t.getName() + "\" " + e.toString());

        for (StackTraceElement se : ses) {
            System.err.println("\tat " + se);
        }

        Throwable ec = e.getCause();
        if (null != ec) {
            uncaughtException(t, ec);
        }
    }
}
