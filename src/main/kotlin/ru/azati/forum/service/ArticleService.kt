package ru.azati.forum.service

import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import ru.azati.forum.service.dto.ArticleRequestDto
import ru.azati.forum.service.dto.ArticleResponseDto

interface ArticleService {

    fun findById(@Positive articleId: Long): ArticleResponseDto

    fun findByTitle(request:String): List<ArticleResponseDto>

    fun create(@Valid articleRequestDto: ArticleRequestDto): ArticleResponseDto

    fun change(@Positive articleId: Long, @Valid articleRequestDto: ArticleRequestDto): ArticleResponseDto

    fun remove(@Positive articleId: Long)

}