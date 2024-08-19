package com.imooc.log.stack.chapter3;

/**
 * <h1>只有在必要的时候才使用异常</h1>
 * */
public class EssentialException {

    // 第一种情况
    public static class Imooc {

        private final String[] courses = {"广告", "优惠券"};

        public String course(int index) {

//            try {
//                return courses[index - 1];
//            } catch (ArrayIndexOutOfBoundsException ex) {
//                return null;
//            }
            return index > courses.length ? null : courses[index - 1];
        }
    }

    // 第二种情况
    public static void sum(int[] nums) {

        int _sum = 0;

//        try {
//            int i = 0;
//            while (true) {
//                _sum += nums[i++];
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//            System.out.println("_sum is: " + _sum);
//        }

        for (int i = 0; i != nums.length; ++i) {
            _sum += nums[i];
        }

        System.out.println("_sum is: " + _sum);
    }

    // 第三种情况
    public static void printCourseLen(int index) {

        String course = new Imooc().course(index);

        try {
            System.out.println(course.length());
        } catch (NullPointerException ex) {
            System.out.println(0);
        }

        // 两种方式去对待这样的问题
        // 1. 与第一种情况类似, 判断 course
        // 2. 直接使用 course, 不做任何判断, 让异常尽早抛出
    }
}

这段代码展示了如何在 Java 中合理使用异常处理机制，特别是在处理数组越界和空指针异常时。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **数组访问**:
   - `Imooc` 类中的 `course` 方法接受一个索引值，并尝试从数组中返回对应的课程名称。
   - 原始版本使用 `try-catch` 块来捕获 `ArrayIndexOutOfBoundsException`，并在异常发生时返回 `null`。
   - 优化后的版本通过简单的条件判断来避免异常的发生，提高了效率。

2. **数组求和**:
   - `sum` 方法接受一个整数数组，并计算数组元素的总和。
   - 原始版本使用无限循环和 `try-catch` 块来捕获 `ArrayIndexOutOfBoundsException`，并在异常发生时终止循环并打印结果。
   - 优化后的版本使用普通的 `for` 循环来遍历数组，避免了异常处理的开销。

3. **字符串长度计算**:
   - `printCourseLen` 方法接受一个索引值，获取课程名称，并尝试打印该名称的长度。
   - 原始版本使用 `try-catch` 块来捕获 `NullPointerException`，并在异常发生时打印 `0`。
   - 可以通过条件判断来避免异常的发生，或者直接抛出异常以尽快发现问题。

### 调优思想

1. **避免不必要的异常处理**:
   - 在可以预见的情况下，尽量避免使用异常处理机制来处理正常的流程控制。
   - 例如，在数组访问时，可以通过条件判断来避免数组越界异常。

2. **异常用于异常情况**:
   - 异常应该仅用于处理真正的异常情况，即那些不可预见的错误或异常情况。
   - 正常的流程控制（如数组访问、循环终止等）应通过常规的编程结构来实现。

3. **简化代码**:
   - 通过简化代码结构，减少不必要的异常处理，可以使代码更加简洁、高效。
   - 使用简单的条件判断替代异常处理可以提高代码的可读性和性能。

4. **尽早暴露问题**:
   - 如果确实需要处理潜在的异常情况（如空指针），应该考虑是否需要立即处理这些问题。
   - 有时，让异常尽早抛出可以帮助更快地定位问题。

5. **异常的性能影响**:
   - 异常处理涉及到堆栈跟踪和资源的分配，这会导致一定的性能开销。
   - 优化代码以减少异常处理的使用可以提高程序的性能。

### 实际应用场景

在实际应用中，这种合理使用异常处理的方式适用于以下场景：
- 当你需要确保程序的性能和效率时。
- 当你需要避免使用异常处理来处理正常的流程控制时。
- 当你需要确保异常仅用于处理真正的异常情况时。

总结来说，这段代码示例通过展示如何合理使用异常处理机制，体现了避免不必要的异常处理、简化代码结构、尽早暴露问题等调优思想。这对于提高代码的性能和可维护性非常重要。