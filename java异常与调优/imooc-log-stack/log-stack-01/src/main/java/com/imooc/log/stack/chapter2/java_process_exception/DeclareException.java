package com.imooc.log.stack.chapter2.java_process_exception;

import java.io.EOFException;
import java.io.FileNotFoundException;

/**
 * <h1>方式一: 声明异常</h1>
 * throw, throws
 * */
public class DeclareException {

    /**
     * <h2>使用 throw 关键字抛出运行时异常</h2>
     * */
    private static boolean validate01(String name) {

        if (null == name) {
            throw new NullPointerException("name is null...");
        }

        return "qinyi".equals(name);
    }

    /**
     * <h2>编译期异常, 必须处理这个异常, 或者是由 throws 继续抛出给上层调用者处理</h2>
     * */
    private static void validate02(String name) throws EOFException,
            FileNotFoundException {

        if (null == name) {
            throw new EOFException("name is null...");
        }

        if (!"qinyi".equals(name)) {
            throw new FileNotFoundException("name is not qinyi...");
        }
    }
}


这段代码展示了如何在 Java 中声明和抛出异常，特别是如何区分运行时异常和编译时异常。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **运行时异常**:
   - `validate01` 方法抛出 `NullPointerException`，如果传入的 `name` 参数为 `null`。
   - 这是一个运行时异常，因为它是 `RuntimeException` 的子类，所以在调用该方法的地方不需要显式处理这个异常。

2. **编译时异常**:
   - `validate02` 方法抛出 `EOFException` 和 `FileNotFoundException`，这两种异常都是 `Exception` 的子类，因此是编译时异常。
   - 当方法抛出编译时异常时，必须在方法签名中使用 `throws` 关键字声明这些异常，或者在方法体内捕获并处理这些异常。

### 调优思想

1. **异常分类**:
   - 区分运行时异常和编译时异常可以更好地管理异常处理逻辑。
   - 运行时异常通常用于表示程序错误，如空指针引用等，这些异常通常不应该发生。
   - 编译时异常用于表示应用程序运行中可能出现的正常情况下的异常，如文件不存在等，这些异常需要被预期并妥善处理。

2. **异常声明**:
   - 当方法可能抛出编译时异常时，应该在方法签名中使用 `throws` 关键字声明这些异常，这使得调用者明确知道该方法可能抛出的异常类型。
   - 这样的做法有助于调用者决定如何处理这些异常，比如通过捕获并处理它们，或者再次声明并抛出这些异常给更高的层级。

3. **异常传播**:
   - 如果一个方法抛出了编译时异常，而该方法本身不打算处理这些异常，那么应该使用 `throws` 关键字将这些异常向上抛出，由更高层次的方法处理。

4. **异常的清晰性**:
   - 使用具体的异常类型而不是通用的异常类型，可以提供更准确的错误信息，有助于问题的快速定位。

5. **避免异常吞咽**:
   - 不应该简单地捕获异常而不做任何处理，除非有明确的理由这样做。否则，应该通过日志记录或错误处理来确保异常被妥善处理。

### 实际应用场景

在实际应用中，这种异常声明和处理方式适用于以下场景：
- 当你想要区分程序错误（运行时异常）和正常业务流程中的异常情况（编译时异常）时。
- 当你需要向调用者明确指示某个方法可能抛出的异常类型时。
- 当你需要确保异常被正确处理而不是被简单地忽略时。

总结来说，这段代码示例通过展示运行时异常和编译时异常的不同处理方式，体现了异常分类、异常声明、异常传播和异常清晰性的调优思想。这对于提高代码的质量和可维护性非常重要。