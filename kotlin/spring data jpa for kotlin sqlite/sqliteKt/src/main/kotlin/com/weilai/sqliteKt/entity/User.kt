package com.weilai.sqliteKt.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "email", unique = true)
    val email: String? = null,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null,
) {
    constructor() : this(null, "", "", null, null)
}