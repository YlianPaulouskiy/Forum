package ru.azati.forum.service.dto

data class CommentRequestDto(
    val content: String,
    val userId: Long,
    val articleId: Long
)
