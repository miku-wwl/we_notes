package com.weilai.sqliteKt.repository

import com.weilai.sqliteKt.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>