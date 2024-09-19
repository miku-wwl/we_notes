package com.weilai.kotpa.service

import com.weilai.kotpa.entity.Book
import com.weilai.kotpa.repository.BookRepository
import org.springframework.stereotype.Service


@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun save(book: Book) = bookRepository.save(book)
    fun findByTitle(title: String) = bookRepository.findByTitle(title)
}