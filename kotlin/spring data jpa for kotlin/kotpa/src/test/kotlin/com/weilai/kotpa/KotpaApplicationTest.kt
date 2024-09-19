package com.weilai.kotpa

import com.weilai.kotpa.entity.Author
import com.weilai.kotpa.entity.Book
import com.weilai.kotpa.repository.AuthorRepository
import com.weilai.kotpa.repository.BookRepository
import com.weilai.kotpa.service.AuthorService
import com.weilai.kotpa.service.BookService
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
    lateinit var authorRepository: AuthorRepository

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