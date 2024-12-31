package ru.azati.forum.service.dto

import jakarta.validation.constraints.NotBlank

data class ArticleRequestDto(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String
)
