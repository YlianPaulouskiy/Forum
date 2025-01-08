package ru.azati.forum.service.dto

data class CommentResponseDto(
    val id: Long,
    val content:String,
    val userId: Long,
    val articleId:Long
)