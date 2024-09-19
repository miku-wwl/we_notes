package com.weilai.kotpa.service

import com.weilai.kotpa.entity.Author
import com.weilai.kotpa.repository.AuthorRepository
import org.springframework.stereotype.Service


@Service
class AuthorService(
    private val authorRepository: AuthorRepository
) {
    fun save(author: Author) = authorRepository.save(author)
    fun findByFirstName(firstName: String) = authorRepository.findByFirstName(firstName)
}