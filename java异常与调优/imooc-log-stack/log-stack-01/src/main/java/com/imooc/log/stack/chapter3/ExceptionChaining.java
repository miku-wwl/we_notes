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


这段代码展示了如何在 Java 中使用异常链来处理和传播异常，特别关注于如何保持异常信息的完整性和可追溯性。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **异常类定义**:
   - 定义了三个异常类：`BizException`、`ImoocNullException` 和 `ImoocMathException`。
   - `BizException` 是一个基本的业务异常类，继承自 `Exception`。
   - `ImoocNullException` 和 `ImoocMathException` 分别继承自 `BizException`，用于处理特定的异常情况。

2. **异常抛出**:
   - `validate` 方法检查字符串 `num` 是否为 `null`，如果是，则抛出 `ImoocNullException`。
   - `calculate` 方法尝试将两个字符串转换为整数并相加，如果转换失败，则抛出 `ImoocMathException`。
   - `process` 方法调用 `validate` 和 `calculate` 方法，并捕获 `BizException` 类型的异常，然后再次抛出一个新的 `BizException`，并将原始异常作为 cause 传递。

3. **异常处理**:
   - `main` 方法中调用 `process` 方法，并捕获 `BizException` 类型的异常，最后打印异常的堆栈信息。

### 调优思想

1. **异常链**:
   - 通过将原始异常作为新异常的 cause，可以构建异常链，这有助于保留异常的完整信息。
   - 这样做有助于调试和理解异常发生的上下文。

2. **异常分类**:
   - 通过定义特定的异常类，可以根据异常的具体情况进行更精细的错误处理。
   - 这有助于提高代码的可读性和可维护性。

3. **异常传播**:
   - 在 `process` 方法中捕获异常并再次抛出一个新的异常，同时保留原始异常作为 cause。
   - 这样做可以确保异常信息在整个调用栈中的传播，并且在最终处理异常时能够获得完整的异常信息。

4. **异常信息的完整性**:
   - 通过使用异常链，即使异常经过多层处理，也可以保持异常信息的完整性，这对于问题定位非常有帮助。

5. **异常处理的一致性**:
   - 通过定义一个基本的异常类 `BizException` 和特定的异常类，可以确保在处理异常时保持一致性。
   - 这有助于简化异常处理逻辑，并确保所有异常都以相同的方式处理。

### 实际应用场景

在实际应用中，这种异常链的使用适用于以下场景：
- 当你需要在多层调用中传播异常时。
- 当你需要保留异常的完整信息以进行问题定位时。
- 当你需要确保异常处理逻辑的一致性时。

总结来说，这段代码示例通过展示如何使用异常链来处理和传播异常，体现了异常链的使用、异常分类、异常传播、异常信息的完整性和异常处理的一致性等调优思想。这对于提高代码的可读性、可维护性和调试的便利性非常重要。