package com.imooc.log.stack.chapter6;

import lombok.Data;

/**
 * <h1>学会解决显性问题</h1>
 * */
public class ExplicitBug {

    /**
     * <h2>通用对象</h2>
     * */
    @Data
    private static class GeneralObject {

        private String name;
        private int age;
        private String gender;
    }

    // 不要修复一个 Bug, 又引入了另一个 Bug
    // 可以看到, 有多个地方使用到了 GeneralObject, 所以, 如果需要修改, 会影响到很多地方, 两种方式
    // 1. 仍旧修改 GeneralObject, 但是只增不减属性, 且给出新增加的默认值, 以及合适的构造函数
    // 2. 新写一个对象去修改业务逻辑

    private GeneralObject process01(GeneralObject object) {
        return null;
    }

    private GeneralObject process02(GeneralObject object) {
        return null;
    }

    private GeneralObject process03(GeneralObject object) {
        return null;
    }
}


这段代码示例主要关注的是如何在修改现有代码时避免引入新的问题，同时也强调了解决显性问题的重要性。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **定义 GeneralObject 类**:
   - `GeneralObject` 是一个简单的 POJO 类，包含三个字段：`name`, `age`, 和 `gender`。
   - 通过 Lombok 的 `@Data` 注解自动生成了 getter, setter, 构造器等方法。

2. **处理方法**:
   - `process01`, `process02`, 和 `process03` 方法接收一个 `GeneralObject` 对象作为参数，并返回一个新的 `GeneralObject` 对象。
   - 目前这些方法都只是返回 `null`，没有实现具体的逻辑。

### 调优思想

1. **避免引入新的 Bug**:
   - 当你修复了一个已知的问题（显性问题）时，应当尽量避免引入新的问题（隐性问题）。
   - 在这个例子中，如果需要修改 `GeneralObject` 类，那么应当小心不要破坏现有的功能或者引入新的错误。

2. **代码复用与扩展性**:
   - 由于 `GeneralObject` 类在多个地方被使用，直接修改可能会导致影响范围扩大，因此建议采取审慎的方式来进行变更。
   - 代码中提到的两种方法都是为了保证代码的稳定性和可维护性：
     - 第一种方法是只增加属性并给它们设定合理的默认值，同时提供合适的构造函数。这样不会影响现有的代码，但会使得类更加复杂。
     - 第二种方法是创建一个新的类来承载变化的逻辑，这样可以保持原有类的稳定性，同时使得新的类能够专注于新的需求。

3. **最小改动原则**:
   - 当进行代码修改时，尽可能做出最小的改变来解决问题，以减少引入新问题的可能性。
   - 在这个例子中，如果 `GeneralObject` 需要扩展，那么可以考虑添加新的属性，而不是修改原有的属性。

4. **重构与模块化**:
   - 如果发现某个类的功能需要扩展，那么考虑将其拆分成更小、更专注的类，或者通过继承来扩展功能。
   - 这样做可以提高代码的可读性和可维护性。

### 实际应用场景

在实际开发中，经常需要对现有系统进行修改以满足新的需求或修复已知的问题。在这个过程中，我们需要遵循一些基本原则来确保代码的稳定性和可维护性。

1. **增量式修改**:
   - 在修改类时，尽量只增加属性而不移除现有的属性，同时提供默认值以兼容旧的实现。

2. **使用继承或组合**:
   - 如果需要扩展一个类的功能，可以通过继承或组合的方式来扩展，而不是直接修改原有类。

3. **重构现有代码**:
   - 如果发现类变得过于复杂或者难以维护，可以考虑重构，比如将大类拆分为更小的类。

4. **单元测试**:
   - 在进行任何更改之前，最好为现有的功能编写单元测试，以便验证修改后的代码仍然能够正常工作。

总结来说，这段代码示例通过展示如何处理类的扩展和修改，体现了避免引入新 Bug、代码复用与扩展性、最小改动原则、重构与模块化等调优思想。这对于提高代码的质量和健壮性非常重要。