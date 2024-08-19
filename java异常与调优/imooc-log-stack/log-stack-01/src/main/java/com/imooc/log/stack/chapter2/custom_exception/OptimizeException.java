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


这段代码展示了如何定义一个自定义异常类 `CustomException`，并且在方法 `fun` 中使用它来处理特定的条件不满足的情况。让我们逐步解析这段代码，并讨论其中体现的调优思想。

### 代码解析

1. **自定义异常类 `CustomException`**:
   - 定义了一个静态内部类 `CustomException` 继承自 `RuntimeException`，这是一个运行时异常类，表示不需要在调用栈中显式声明的异常。
   - `CustomException` 类包含两个成员变量 `key` 和 `message`，分别用来存储异常的键值和描述信息。
   - 构造函数接收这两个参数，并通过 `String.format` 方法格式化异常信息。
   - 重写了 `fillInStackTrace` 方法，返回自身而不是创建新的堆栈跟踪，这意味着不会生成详细的堆栈跟踪信息，这可以减少内存消耗。
   - 重写了 `toString` 方法，以便在打印异常时输出定制化的信息。

2. **异常抛出**:
   - `fun` 方法检查传入的字符串 `name` 是否等于 `"qinyi"`，如果不等于，则抛出 `CustomException`。

3. **异常触发**:
   - `main` 方法调用了 `fun` 方法，并传入了一个字符串 `"imooc-qinyi"`，这个值不等于 `"qinyi"`，因此会触发异常。

### 调优思想

1. **减少内存占用**:
   - 通过重写 `fillInStackTrace` 方法返回自身，避免了创建额外的堆栈跟踪信息，从而减少了内存占用。这种做法对于性能敏感的应用程序尤其有用。

2. **定制化的异常信息**:
   - 自定义异常类允许你根据需要提供更具体的信息，例如通过 `key` 和 `message` 提供上下文相关的异常描述，这有助于快速定位问题。

3. **简化异常处理**:
   - 通过自定义异常类，你可以简化异常处理逻辑，只关注那些真正重要的异常情况，而不是让程序被过多的异常处理逻辑所困扰。

4. **异常的可读性和可维护性**:
   - 通过提供清晰的异常信息和结构化的异常处理机制，可以提高代码的可读性和可维护性。

5. **性能优化**:
   - 在性能敏感的应用程序中，避免不必要的堆栈跟踪信息的生成可以提高程序的整体性能。

### 实际应用场景

在实际应用中，自定义异常类通常用于以下场景：
- 当标准的异常类无法准确描述你的业务需求时。
- 需要提供额外的信息来帮助诊断问题。
- 对性能有较高要求，希望减少异常处理带来的开销。

总结来说，这段代码示例通过定义自定义异常类并优化其行为，体现了减少内存占用、提高异常处理的可读性和可维护性的调优思想。这对于构建高性能和易于维护的应用程序是非常有价值的。