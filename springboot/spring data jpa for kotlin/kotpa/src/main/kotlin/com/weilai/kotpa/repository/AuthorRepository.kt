package com.weilai.kotpa.repository

import com.weilai.kotpa.entity.Author
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByFirstName(firstName: String): Author
}