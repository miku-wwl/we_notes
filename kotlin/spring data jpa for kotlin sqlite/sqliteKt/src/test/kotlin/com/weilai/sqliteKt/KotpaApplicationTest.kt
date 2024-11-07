package com.weilai.sqliteKt

import com.weilai.sqliteKt.repository.UserRepository
import com.weilai.sqliteKt.service.AuthorService
import com.weilai.sqliteKt.service.BookService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class KotpaApplicationTest() {
    @Autowired
    lateinit var authorService: AuthorService

    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var authorRepository: UserRepository

    @Autowired
    lateinit var bookRepository: BookRepository

    @BeforeEach
    fun init() {
        authorService.save(Author(firstName = "wang", lastName = "weilai"))
        bookService.save(Book(title = "cs", authorId = 1))

    }

    @Test
    fun testFind() {
        val auth = authorService.findByFirstName("wang")
        val book = bookService.findByTitle("cs")
        println(auth)
        println(book)
    }


    @AfterEach
    fun deleteAll() {
        authorRepository.deleteAll()
        bookRepository.deleteAll()
    }
}