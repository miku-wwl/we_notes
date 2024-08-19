package com.imooc.log.stack.chapter2.java_process_exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h1>方式2: 捕获异常</h1>
 * try...catch...finally
 * */
@SuppressWarnings("all")
public class CatchException {

    /**
     * <h2>validate01 抛出单个异常</h2>
     * */
    private static boolean validate01(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        return "qinyi".equals(name);
    }

    /**
     * <h2>validate02 抛出多个异常</h2>
     * */
    private static boolean validate02(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        if ("".equals(name)) {
            throw new IllegalArgumentException("name is blank...");
        }

        if (!"qinyi".equals(name)) {
            throw new RuntimeException("name is not qinyi...");
        }

        return true;
    }

    /**
     * <h2>打开并关闭 Stream</h2>
     * */
    private static void openAndCloseStream() {

        Stream<Path> pathStream = null;

        try {
            pathStream = Files.list(Paths.get("/tmp"));
            List<Path> paths = pathStream.collect(Collectors.toList());
            System.out.println(paths.size());
            // ....
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != pathStream) {
                pathStream.close();
            }
        }
    }

    public static void main(String[] args) {
//
//        // 1. 捕获单个异常
//        try {
//            validate01(null);
//        } catch (Throwable th) {
//            System.out.println(th.getMessage());
//            th.printStackTrace();
//        }
//
//        // 2.1 捕获多个异常 -- 第一种方法, 多一个异常一次捕获多次处理
//        try {
//            validate02("");
//        } catch (NullPointerException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } catch (IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
//
//        // 2.2 捕获多个异常 -- 第二种方式, 一个 try, 一个 catch
//        try {
//            validate02("");
//        } catch (NullPointerException | IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }
//
//        // 2.3 捕获多个异常 -- 第三种方式, 定义一个范围更大的父类异常对象
//        try {
//            validate02("");
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        }

        openAndCloseStream();
    }
}


这段代码展示了如何在 Java 中处理异常，特别是如何捕获和处理不同类型的异常。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **异常抛出**:
   - `validate01` 方法抛出 `NullPointerException`，如果传入的 `name` 参数为 `null`。
   - `validate02` 方法根据不同的条件抛出不同的异常：`NullPointerException`、`IllegalArgumentException` 和 `RuntimeException`。

2. **异常捕获**:
   - `main` 方法中演示了不同的异常捕获方式：
     - 单个异常捕获：使用单一的 `try-catch` 块来捕获 `validate01` 方法抛出的 `NullPointerException`。
     - 多个异常捕获：
       - 第一种方法：为每种异常类型定义单独的 `catch` 块。
       - 第二种方法：使用 `|` 运算符在一个 `catch` 块中捕获多种异常类型。
       - 第三种方法：捕获一个更广泛的异常类型，如 `RuntimeException`，以捕获所有运行时异常。
   - `openAndCloseStream` 方法展示了如何安全地打开和关闭 `Stream`，并在出现异常时进行适当的处理。

3. **资源管理**:
   - 在 `openAndCloseStream` 方法中，通过 `try-catch-finally` 结构确保了 `Stream` 被正确关闭，即使发生了异常。

### 调优思想

1. **异常捕获的粒度**:
   - 根据异常类型捕获异常，可以更精确地处理不同的错误情况。
   - 对于简单的异常，可以使用单一的 `catch` 块来捕获。
   - 对于复杂的异常情况，可以使用多个 `catch` 块来分别处理不同类型的异常，或者使用一个更宽泛的异常类型来捕获多种异常。

2. **资源管理**:
   - 使用 `try-catch-finally` 结构确保了在异常发生时资源被正确释放，这有助于防止资源泄露。

3. **异常信息的利用**:
   - 在捕获异常后，通过打印异常信息或使用异常对象的方法（如 `getMessage()`），可以提供更多关于错误的信息，有助于调试和问题定位。

4. **避免异常吞咽**:
   - 在 `finally` 块中关闭资源是一个好习惯，但要注意不要简单地忽略异常，而是应该适当地处理它们。

5. **简化异常处理**:
   - 使用 `|` 运算符在一个 `catch` 块中捕获多种异常类型可以简化代码，使其更易阅读。

### 实际应用场景

在实际应用中，这种异常处理方式适用于以下场景：
- 当你需要对不同类型的异常采取不同的处理措施时。
- 当你需要确保资源在异常发生时被正确释放时。
- 当你希望保持代码的简洁性和可读性时。

总结来说，这段代码示例通过展示不同的异常捕获和处理方式，体现了异常处理的粒度控制、资源管理和异常信息的有效利用等调优思想。这对于提高代码的质量和可维护性非常重要。