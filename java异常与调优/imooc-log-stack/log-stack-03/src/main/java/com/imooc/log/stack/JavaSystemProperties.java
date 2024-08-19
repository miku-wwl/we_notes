package com.imooc.log.stack;

import java.util.Properties;

/**
 * <h1>Java 系统属性</h1>
 * */
public class JavaSystemProperties {

    public static void main(String[] args) {

        // 打印所有的 Java 系统属性
        Properties pros = System.getProperties();
        pros.list(System.out);

        System.out.println("//////////////////////////////////////////////////////////////////");

        // 获取特定的 Java 系统属性, key 不存在则返回 null
        System.out.println(System.getProperty("java.home"));        // JRE 主目录
        System.out.println(System.getProperty("java.library.path"));        // 用于搜索本机库的 JRE 库搜索路径
        System.out.println(System.getProperty("java.ext.dirs"));        // JRE扩展库路径
        System.out.println(System.getProperty("java.class.path"));      // JRE类路径
        System.out.println(System.getProperty("java.version"));     // Java 版本
        System.out.println(System.getProperty("imooc-qinyi"));
    }
}


这段代码展示了如何使用 Java 的 `System.getProperties()` 方法来获取 Java 运行时环境 (JRE) 的系统属性，并打印出一些特定的属性值。让我们逐行分析这段代码，并探讨其背后的调优思想。

### 代码解析

1. **导入必要的包**:
   ```java
   import java.util.Properties;
   ```
   导入 `Properties` 类，它是 Java 中用于处理键值对集合的一个类。

2. **主类定义**:
   ```java
   public class JavaSystemProperties {
       public static void main(String[] args) {
           // ...
       }
   }
   ```
   定义了一个名为 `JavaSystemProperties` 的公共类，并且在其中定义了一个 `main` 方法。

3. **获取所有系统属性**:
   ```java
   Properties pros = System.getProperties();
   pros.list(System.out);
   ```
   这里使用 `System.getProperties()` 获取当前 JVM 的所有系统属性，并将它们打印到控制台。这些属性包括操作系统类型、JVM 版本、安装路径等。

4. **打印特定的系统属性**:
   ```java
   System.out.println(System.getProperty("java.home"));        // JRE 主目录
   System.out.println(System.getProperty("java.library.path"));        // 用于搜索本机库的 JRE 库搜索路径
   System.out.println(System.getProperty("java.ext.dirs"));        // JRE扩展库路径
   System.out.println(System.getProperty("java.class.path"));      // JRE类路径
   System.out.println(System.getProperty("java.version"));     // Java 版本
   System.out.println(System.getProperty("imooc-qinyi"));
   ```
   这段代码打印了一些特定的系统属性值：
   - `"java.home"`: 当前 JRE 的主目录。
   - `"java.library.path"`: 用于搜索本地库的路径。
   - `"java.ext.dirs"`: JRE 扩展库的路径。
   - `"java.class.path"`: 类路径，即类文件的查找路径。
   - `"java.version"`: 当前运行的 Java 版本。
   - `"imooc-qinyi"`: 如果这个属性没有在系统属性中定义，则会返回 `null`。

### 调优思想

1. **性能监控**:
   - 通过检查 `java.home` 和 `java.version` 可以确认运行时环境是否符合应用程序的要求。
   - 查看 `java.class.path` 可以帮助理解类路径设置是否正确，这可能影响到类加载的性能。

2. **异常定位**:
   - 在遇到问题时，了解 `java.library.path` 可以帮助确定是否缺少必要的本地库。
   - `java.ext.dirs` 可以用来确认是否有不兼容的扩展被加载。

3. **调试和故障排除**:
   - 使用 `System.getProperty` 可以帮助快速定位问题所在，例如版本不匹配导致的问题。
   - 通过检查 `java.class.path` 可以帮助发现是否正确的依赖库被加载到了类路径中。

4. **代码简洁性**:
   - 使用 `System.getProperty` 比手动构造字符串更简洁且不易出错。
   - 代码结构清晰，易于理解和维护。

5. **标准化**:
   - 使用标准的 API 来获取系统属性，而不是硬编码这些信息，使得代码更具通用性和可移植性。

6. **安全性**:
   - 确认 `java.home` 和 `java.version` 等属性可以帮助确保应用程序运行在一个受支持和安全的环境中。
   - 通过检查 `java.class.path` 可以确保不会加载恶意或不安全的类库。

### 实际应用场景

在实际开发过程中，这种代码可以用在以下场景：
- 验证运行环境是否满足应用需求。
- 故障排查时检查环境配置。
- 日志记录中加入系统属性信息，便于日后分析问题。

### 总结

这段代码展示了如何使用 Java 的内置功能来获取和打印系统属性，这对于调优来说非常重要。它能够帮助开发者更好地理解应用程序的运行环境，并在出现问题时提供有用的线索。这种做法体现了性能监控、异常定位、调试和故障排除、代码简洁性、标准化和安全性等方面的调优思想。