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


这段代码展示了如何在 Java 中处理和传播异常，并且试图展示一种方法来确保异常堆栈的完整性。我们来逐行分析这段代码。

### 代码解析

1. **异常抛出**:
   - `imooc1` 方法直接抛出了一个 `Exception`，这是异常传播的起点。
   - `imooc2` 方法通过调用 `imooc1` 并捕获异常，然后重新抛出一个新的 `Exception`，同时将原始异常作为构造函数的参数传递，这样原始异常就变成了新异常的原因（cause）。
   - `imooc3` 方法调用 `imooc2` 并以类似的方式处理异常，但这次是抛出了一个 `RuntimeException`。

2. **异常捕获**:
   - 在 `main` 方法中，原本打算在一个 try-catch 块中调用 `imooc3` 并打印异常堆栈，但是这部分被注释掉了。
   - 为了能够捕捉到未捕获的异常（即 `RuntimeException`），程序设置了默认的未捕获异常处理器 `Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());`。
   - 接着调用 `imooc3()` 方法，由于它可能抛出 `RuntimeException`，如果没有这个处理器，程序将会终止并打印异常信息；有了这个处理器之后，异常会被传递给 `ExceptionHandler` 类进行处理。

3. **ExceptionHandler 类**:
   - 这个类没有给出具体实现，但我们可以假设它是一个实现了 `Thread.UncaughtExceptionHandler` 接口的类，用于处理未捕获的异常。
   - 当一个线程抛出未被捕获的异常时，这个处理器会执行，通常会打印异常的详细信息或者进行其他错误处理操作。

### 调优思想

- **保持异常堆栈完整**:
  - 通过将原始异常作为新异常的 cause，可以保留原始异常的信息，这对于调试非常有用。
  
- **适当的异常处理**:
  - 不要简单地吞掉异常，而是应该适当处理或至少记录下来，以便于问题追踪和解决。
  
- **使用合适的异常类型**:
  - 根据异常情况选择使用 `RuntimeException` 或者 `Exception`，前者通常用于编程错误，后者则用于可预见的异常情况。
  
- **未捕获异常处理**:
  - 设置全局的未捕获异常处理器可以确保即使在某些情况下异常没有被捕获，也能够得到适当的处理和记录。

### 总结

这段代码示例主要展示了如何在 Java 中维护异常堆栈的完整性，以及如何通过设置全局未捕获异常处理器来确保所有异常都被正确处理。这种做法有助于提高系统的稳定性和可维护性。