package com.imooc.log.stack.chapter2.custom_exception;

/**
 * <h1>为业务/框架定义的专用异常</h1>
 * */
public class ProfessionalException {

    /**
     * <h2>自定义异常的基类</h2>
     * */
    public static class BizException extends RuntimeException {

        // 定义错误码, 还可以继续扩展定义各种属性
        private String errorCode;

        public BizException(String errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }
    }

    /**
     * <h2>商品模块异常类: 查看商品</h2>
     * */
    public static class ViewGoodsException extends BizException {

        public ViewGoodsException(String errorCode, String message) {
            super(errorCode, message);
        }
    }
}

这段代码展示了如何定义专门针对业务或框架使用的异常类，并且按照模块进行了分类。让我们来逐个解析这些类，并讨论它们体现了哪些调优思想。

### 代码解析

1. **自定义异常基类 `BizException`**:
   - `BizException` 是一个继承自 `RuntimeException` 的异常类，它用于定义所有业务异常的基础。
   - 包含一个 `errorCode` 字段，用于标识具体的错误代码。
   - 构造函数接收错误代码和错误消息作为参数，并调用父类的构造函数设置错误消息。
   
2. **商品模块异常类 `ViewGoodsException`**:
   - `ViewGoodsException` 是 `BizException` 的子类，用于商品模块中的异常。
   - 它同样接收错误代码和错误消息作为参数，并通过调用父类的构造函数传递这些信息。

### 调优思想

1. **异常层次结构**:
   - 通过定义异常层次结构，可以更好地组织异常类型。这样可以让开发者更容易理解和处理不同类型的异常。
   - `BizException` 作为基础异常类，可以被其他特定业务模块的异常类继承，这样保持了一致性的同时也提供了灵活性。

2. **错误代码**:
   - 引入 `errorCode` 字段可以帮助快速识别错误的来源和类型。这对于大型系统尤其重要，因为它可以帮助快速定位问题。
   - 错误代码通常与错误消息一起使用，以提供更多的上下文信息。

3. **模块化异常**:
   - 按照业务模块定义异常类（如 `ViewGoodsException`），可以使异常处理更加清晰，更容易维护。
   - 每个模块可以有自己的异常处理逻辑，这有助于分离关注点。

4. **可扩展性**:
   - 使用继承结构定义异常，可以很容易地添加新的异常类型而不需要修改现有的代码。
   - 如果未来需要添加更多关于商品的操作（比如购买、库存管理等），可以轻松地扩展异常类。

5. **减少冗余**:
   - 通过使用继承，可以避免在每个异常类中重复相同的代码（如错误代码的处理逻辑）。

6. **可读性和可维护性**:
   - 清晰的异常类命名和结构有助于提高代码的可读性和可维护性。

### 实际应用场景

在实际应用中，这种层次化的异常设计通常用于以下场景：
- 当应用程序包含多个不同的业务模块时，可以为每个模块定义特定的异常类。
- 需要提供详细的错误信息以便于快速定位和解决问题。
- 希望保持异常处理逻辑的一致性和简洁性。

总结来说，这段代码示例通过定义专门的异常类并按照业务模块进行分类，体现了提高代码的可读性、可维护性以及减少冗余的调优思想。这对于构建复杂的应用程序是非常有益的。