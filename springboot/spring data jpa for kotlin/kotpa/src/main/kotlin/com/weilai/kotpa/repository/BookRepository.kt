package com.weilai.kotpa.repository

import com.weilai.kotpa.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByTitle(title: String): Book
}