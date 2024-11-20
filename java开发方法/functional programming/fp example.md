``` java
package com.weilai.executor.function;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class FunctionExamples {

    public static void main(String[] args) {
        function();
        stream();
        stream2();
    }

    private static void stream2() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 过滤出偶数
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);

        // 将每个数字乘以2
        List<Integer> doubledNumbers = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());
        System.out.println("Doubled numbers: " + doubledNumbers);

        // 去重
        List<Integer> uniqueNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique numbers: " + uniqueNumbers);

        // 按降序排序
        List<Integer> sortedNumbers = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("Sorted numbers: " + sortedNumbers);

        // 获取前5个元素
        List<Integer> firstFive = numbers.stream()
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("First five numbers: " + firstFive);

        // 跳过前3个元素
        List<Integer> afterSkip = numbers.stream()
                .skip(3)
                .collect(Collectors.toList());
        System.out.println("After skip: " + afterSkip);

        // 计算总和
        Optional<Integer> sum = numbers.stream()
                .reduce(Integer::sum);
        sum.ifPresent(System.out::println); // 输出: 55

        // 打印每个元素
        numbers.stream()
                .forEach(System.out::println);
    }

    private static void stream() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(evenNumbers);

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        List<String> upperCaseWords = words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(upperCaseWords);

        List<String> words2 = Arrays.asList("abc", "def");
        List<Character> characters = words.stream()
                .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                .collect(Collectors.toList());
        System.out.println(characters);


        List<Integer> numbers1 = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        List<Integer> uniqueNumbers = numbers1.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueNumbers);

        List<String> words3 = Arrays.asList("apple", "banana", "cherry");
        List<String> sortedWords = words3.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println(sortedWords);

        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> firstThree = numbers2.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(firstThree);

        List<Integer> numbers3 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> afterSkip = numbers3.stream()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println(afterSkip);

        List<Integer> numbers4 = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers4.stream()
                .forEach(System.out::println);


        List<Integer> numbers5 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Optional<Integer> sum = numbers5.stream()
                .reduce(Integer::sum);
        sum.ifPresent(System.out::println); // 输出: 21


        List<Integer> numbers6 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> collectedList = numbers6.stream()
                .filter(it -> it < 5)
                .collect(Collectors.toList());
        System.out.println(collectedList);

    }

    private static void function() {
        // Function
        Function<String, Integer> stringToInteger = Integer::parseInt;
        System.out.println(stringToInteger.apply("123")); // 输出: 123

        // Predicate
        Predicate<String> isNotEmpty = String::isBlank;
        System.out.println(isNotEmpty.test("")); // 输出: true

        // Consumer
        Consumer<String> printString = System.out::println;
        printString.accept("Hello, World!"); // 输出: Hello, World!

        // Supplier
        Supplier<String> generateString = () -> "Generated String";
        System.out.println(generateString.get()); // 输出: Generated String

        // UnaryOperator
        UnaryOperator<String> toUpperCase = String::toUpperCase;
        System.out.println(toUpperCase.apply("hello")); // 输出: HELLO

        // BiFunction
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(5, 3)); // 输出: 8

        // BiPredicate
        BiPredicate<Integer, Integer> isEqual = Integer::equals;
        System.out.println(isEqual.test(5, 5)); // 输出: true

        // BiConsumer
        BiConsumer<String, String> concatenate = (a, b) -> System.out.println(a + b);
        concatenate.accept("Hello, ", "World!"); // 输出: Hello, World!

        // ToIntFunction
        ToIntFunction<String> stringToInt = Integer::parseInt;
        System.out.println(stringToInt.applyAsInt("123")); // 输出: 123

        // IntFunction
        IntFunction<String> intToString = Integer::toString;
        System.out.println(intToString.apply(123)); // 输出: 123

        // IntPredicate
        IntPredicate isEven = n -> n % 2 == 0;
        System.out.println(isEven.test(4)); // 输出: true

        // IntConsumer
        IntConsumer printInt = System.out::println;
        printInt.accept(123); // 输出: 123

        // IntToDoubleFunction
        IntToDoubleFunction intToDouble = n -> n * 1.5;
        System.out.println(intToDouble.applyAsDouble(2)); // 输出: 3.0
    }
}
```