package ru.azati.forum.service

import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import ru.azati.forum.service.dto.CommentRequestDto
import ru.azati.forum.service.dto.CommentResponseDto

interface CommentService {

    fun findAllByArticle(@Positive articleId: Long, offset:Int = 1, limit:Int = 10): List<CommentResponseDto>

    fun create(@Valid commentRequestDto: CommentRequestDto): CommentResponseDto

    fun remove(@Positive commentId: Long)

    fun change(@Positive commentId: Long, @Valid commentRequestDto: CommentRequestDto): CommentResponseDto

}