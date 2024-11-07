package com.weilai.sqliteKt.controller

import com.weilai.sqliteKt.entity.User
import com.weilai.sqliteKt.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class UserController(
    @Autowired private val userRepository: UserRepository
) {

    @GetMapping("/users")
    fun getUsers(): MutableList<User> = userRepository.findAll()

    @PostMapping("/save-user")
    fun saveUser(@RequestBody user: User): User {
        user.createdAt = LocalDateTime.now()
        return userRepository.save(user)
    }
}