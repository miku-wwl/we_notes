package com.weilai.gradle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Test2Application

fun main(args: Array<String>) {
    runApplication<Test2Application>(*args)
}
