package com.weilai.kotpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotpaApplication

fun main(args: Array<String>) {
    runApplication<KotpaApplication>(*args)
}