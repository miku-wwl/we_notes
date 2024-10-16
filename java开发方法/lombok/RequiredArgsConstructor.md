`@RequiredArgsConstructor` 是 Lombok 库提供的一个注解，用于自动生成一个构造函数，该构造函数包含类中所有被标记为 `final` 的实例变量，或者是被注解为 `@NonNull` 的实例变量。这个构造函数是必需的（required），因为它确保了在对象创建时必须初始化这些不可变（immutable）或非空（non-null）的字段。

### 作用

1. **简化构造函数**：避免手动编写构造函数来初始化不可变或非空字段。
2. **确保不可变性**：确保在对象创建之后，这些字段不能再被修改。
3. **提高代码可读性**：通过注解简化代码，提高代码的可读性和可维护性。

### 使用场景

- 当你需要一个类的所有 `final` 字段都在构造函数中初始化。
- 当你需要确保某些字段在构造函数中必须被初始化，以避免 `null` 值。

### 示例代码

假设你有一个类 `User`，它有一些 `final` 字段和一些非 `final` 字段：

```java
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User {
    private final String name;
    private final int age;

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
}
```

在这个例子中，`name` 和 `age` 是 `final` 字段，所以 `@RequiredArgsConstructor` 会自动生成一个构造函数，接受这两个字段的值：

```java
public User(final String name, final int age) {
    this.name = name;
    this.age = age;
}
```

### 更多细节

1. **与其他注解结合使用**：

   - 你可以将 `@RequiredArgsConstructor` 与其他 Lombok 注解结合使用，例如 `@AllArgsConstructor`、`@NoArgsConstructor` 等，以生成更多类型的构造函数。

   ```java
   import lombok.RequiredArgsConstructor;
   import lombok.AllArgsConstructor;
   import lombok.NoArgsConstructor;

   @NoArgsConstructor
   @AllArgsConstructor
   @RequiredArgsConstructor
   public class User {
       private final String name;
       private final int age;

       private String email;
   }
   ```

2. **非 `final` 字段**：

   - 如果你有一些非 `final` 字段也需要在构造函数中初始化，可以使用 `@NonNull` 注解来标记它们，这样 `@RequiredArgsConstructor` 也会为这些字段生成构造函数。

   ```java
   import lombok.NonNull;
   import lombok.RequiredArgsConstructor;

   @RequiredArgsConstructor
   public class User {
       private final String name;
       private final int age;

       @NonNull
       private String email;

       public void setEmail(String email) {
           this.email = email;
       }

       public String getName() {
           return name;
       }

       public int getAge() {
           return age;
       }

       public String getEmail() {
           return email;
       }
   }
   ```

   在这种情况下，构造函数会包含 `name`、`age` 和 `email`：

   ```java
   public User(final String name, final int age, @NonNull final String email) {
       this.name = name;
       this.age = age;
       this.email = email;
   }
   ```

### 注意事项

1. **IDE 和编译器支持**：确保你的 IDE 和编译器支持 Lombok。在 IntelliJ IDEA 和 Eclipse 中，你需要安装 Lombok 插件。
2. **依赖管理**：确保你的项目依赖中包含了 Lombok 库。
3. **调试和源码生成**：Lombok 生成的构造函数和其他方法不会出现在源代码中，但在编译后的字节码中可以看到。这可能会影响调试体验。

通过使用 `@RequiredArgsConstructor`，你可以简化代码，确保不可变字段在构造函数中被正确初始化，并提高代码的可读性和可维护性。
