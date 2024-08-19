package com.imooc.log.stack.chapter3;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * <h1>其他需要遵守的一些规约</h1>
 * */
public class OtherPrinciples {

    /**
     * <h2>捕获有必要的代码段, 不要大段的使用</h2>
     * */
    public static long suitableTryCatch(String start, String end) {

        LocalDate startDay;
        LocalDate endDay;

        try {
            startDay = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .parse(start));
            endDay = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .parse(end));
        } catch (DateTimeException ex) {
            ex.printStackTrace();
            return -1;
        }

        return startDay.until(endDay, ChronoUnit.DAYS);
    }

    /**
     * <h2>尽量不要捕获 Exception, 而是捕获更加具体的异常</h2>
     * */
    public static void classifyException(String fileName) {

        try {
            FileInputStream file = new FileInputStream(fileName);
            int x = (byte) file.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * <h2>异常处理后要及时的清理, 释放资源</h2>
     * */
    public static void closeResource(String fileName) {

        FileInputStream file = null;

        try {
            file = new FileInputStream(fileName);
            int x = (byte) file.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != file) {
                    file.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * <h2>尽早的让异常暴露出来</h2>
     * */
    public static void earlyException(String input) {

        // 详细的判断
        if (null != input && !"".equals(input) && input.contains("qinyi")) {
            // ....
        } else {
            throw new IllegalArgumentException("error input: " + input);
        }

        // do something
    }
}


这段代码展示了在 Java 中处理异常时需要遵循的一些最佳实践和原则。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **合适的异常捕获**:
   - `suitableTryCatch` 方法接收两个日期字符串，尝试将它们转换为 `LocalDate` 对象，并计算两者之间的天数差。
   - 如果日期格式不正确，则捕获 `DateTimeException` 并返回 `-1`。

2. **具体的异常捕获**:
   - `classifyException` 方法尝试打开一个文件并读取第一个字节。
   - 如果在文件操作过程中发生异常，则捕获 `IOException`。

3. **资源清理**:
   - `closeResource` 方法同样尝试打开一个文件并读取第一个字节。
   - 无论是否发生异常，都会在 `finally` 块中关闭文件输入流。

4. **尽早暴露异常**:
   - `earlyException` 方法检查输入字符串是否符合某些条件。
   - 如果输入不符合条件，则立即抛出 `IllegalArgumentException`。

### 调优思想

1. **合适的异常捕获**:
   - 应该仅捕获那些真正需要处理的异常，避免过度使用异常处理。
   - 在 `suitableTryCatch` 方法中，只捕获了 `DateTimeException`，这是处理日期解析时可能发生的特定异常。

2. **具体的异常捕获**:
   - 尽量捕获具体的异常类型而不是捕获 `Exception` 或 `Throwable`。
   - 在 `classifyException` 方法中，捕获 `IOException` 而不是更广泛的 `Exception` 类型，这样可以更精确地了解发生了什么错误。

3. **资源清理**:
   - 在 `closeResource` 方法中，通过 `finally` 块确保即使发生异常也会释放资源。
   - 这有助于防止资源泄漏，并确保程序的健壮性。

4. **尽早暴露异常**:
   - 在 `earlyException` 方法中，如果输入不符合预期，则立即抛出异常。
   - 这有助于尽早发现问题，并避免后续代码执行不必要的逻辑。

5. **异常的可读性和可维护性**:
   - 通过捕获具体的异常类型和尽早抛出异常，可以提高代码的可读性和可维护性。
   - 明确的异常类型有助于快速定位问题。

### 实际应用场景

在实际应用中，这些原则适用于以下场景：
- 当你需要确保异常处理逻辑既简洁又精确时。
- 当你需要确保资源得到妥善释放时。
- 当你需要确保异常能够被及时发现并处理时。

总结来说，这段代码示例通过展示如何合适地捕获异常、捕获具体的异常类型、确保资源清理以及尽早暴露异常，体现了合适的异常捕获、具体的异常捕获、资源清理和尽早暴露异常等调优思想。这对于提高代码的性能、可读性和可维护性非常重要。