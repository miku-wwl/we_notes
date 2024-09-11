1、安全调用操作符 (?.)

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    val length = nullableString?.length // 如果 nullableString 为 null，length 也将为 null
    println("Length: $length") // 如果 length 为 null，不会抛出异常
}

fun getNullableString(): String? {
    return null
}
```

```
Length: null

Process finished with exit code 0
```

2、Elvis (?:)

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    val length = nullableString?.length ?: -1 // 如果 nullableString 为 null，length 也将为 null
    println("Length: $length") // 如果 length 为 null，不会抛出异常
}

fun getNullableString(): String? {
    return null
}

```

```
Length: -1

Process finished with exit code 0
```

3、非空断言 (!!)

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    val length = nullableString!!.length // 如果 nullableString 为 null，length 也将为 null
    println("Length: $length") // 如果 length 为 null，不会抛出异常
}

fun getNullableString(): String? {
    return null
}

```

```
Exception in thread "main" java.lang.NullPointerException
	at com.weilai.kot.MainKt.main(Main.kt:5)
	at com.weilai.kot.MainKt.main(Main.kt)

Process finished with exit code 1
```

4、let

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    nullableString?.let { notNullString ->
        println("Length: ${notNullString.length}")
    }
}

fun getNullableString(): String? {
    return null
}
```

```

Process finished with exit code 0
```

5、run

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    nullableString?.run {
        println("Length: $length")
        println("Substring: ${this.substring(0, 5)}")
    }
}

fun getNullableString(): String? {
    return null
}
```

```

Process finished with exit code 0
```

6、返回默认值(?:与?.结合)

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    val firstChar = nullableString?.firstOrNull() ?: 'N'
    println("First character: $firstChar")
}

fun getNullableString(): String? {
    return null
}
```

```
First character: N

Process finished with exit code 0
```

7、使用 with 或 apply

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    nullableString?.apply {
        println("Length: $length")
        println("Substring: ${this.substring(0, 5)}")
    }
}

fun getNullableString(): String? {
    return null
}

```

```

Process finished with exit code 0
```

8、使用 requireNotNull 进行验证

```kotlin
fun main() {
    val nullableString: String? = getNullableString()
    val nonNullString = requireNotNull(nullableString) { "String cannot be null" }
    println("Length: ${nonNullString.length}")
}

fun getNullableString(): String? {
    return null
}


```

```

Exception in thread "main" java.lang.IllegalArgumentException: String cannot be null
	at com.weilai.kot.MainKt.main(Main.kt:5)
	at com.weilai.kot.MainKt.main(Main.kt)

Process finished with exit code 1
```
