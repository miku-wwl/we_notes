package com.imooc.log.stack.chapter3;

/**
 * <h1>学会使用异常链</h1>
 * */
public class ExceptionChaining {

    public static class BizException extends Exception {

        public BizException(String message) {
            super(message);
        }

        public BizException(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class ImoocNullException extends BizException {

        public ImoocNullException(String message) {
            super(message);
        }

        public ImoocNullException(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class ImoocMathException extends BizException {

        public ImoocMathException(String message) {
            super(message);
        }

        public ImoocMathException(String message, Throwable t) {
            super(message, t);
        }
    }

    private static void validate(String num) throws ImoocNullException {
        if (null == num) {
            throw new ImoocNullException("num is null");
        }
    }

    private static int calculate(String num1, String num2) throws ImoocMathException {

        try {
            int _num1 = Integer.parseInt(num1);
            int _num2 = Integer.parseInt(num2);

            return _num1 + _num2;
        } catch (NumberFormatException ex) {
//            throw new ImoocMathException("has some error in calculate");
            throw new ImoocMathException("has some error in calculate", ex);
        }
    }

    private static void process() throws BizException {

        try {
            String num1 = "12345";
            String num2 = "qinyi";

            validate(num1);
            validate(num2);

            System.out.println(calculate(num1, num2));
        } catch (BizException ex) {
//            throw new BizException("has some error in process");
            throw new BizException("has some error in process", ex);
        }
    }

    public static void main(String[] args) {

        try {
            process();
        } catch (BizException ex) {
            ex.printStackTrace();
        }
    }
}
