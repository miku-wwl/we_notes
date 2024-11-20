``` java
private static void time() {
    // 获取当前的日期时间
    LocalDateTime now = LocalDateTime.now();

    // 定义日期时间的格式
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 将LocalDateTime对象格式化为字符串
    String formattedDateTime = now.format(formatter);

    // 输出格式化后的日期时间
    System.out.println("Formatted Date and Time: " + formattedDateTime);
}

输出内容： Formatted Date and Time: 2024-11-20 23:37:59
```