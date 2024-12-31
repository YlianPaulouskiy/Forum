package ru.azati.forum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import ru.azati.forum.service.dto.ArticleRequestDto
import ru.azati.forum.service.dto.ArticleResponseDto

@Tag(
    name = "Article Controller",
    description = "Controller responsible for handling requests related to articles."
)
interface ArticleController {

    @Operation(summary = "Find Article By Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Article successful find",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Article not found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponseDto::class)
                )]
            ),
        ]
    )
    fun findById(articleId: Long): ResponseEntity<ArticleResponseDto>

    @Operation(summary = "Find Articles By RequestSearch")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Articles successful find",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(
                        schema = Schema(implementation = ArticleResponseDto::class)
                    )
                )]
            )
        ]
    )
    fun findByTitle(request: String): ResponseEntity<List<ArticleResponseDto>>

    @Operation(summary = "Create Article")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "Article successful created",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ArticleResponseDto::class)
            )]
        )]
    )
    fun create(articleRequestDto: ArticleRequestDto): ResponseEntity<ArticleResponseDto>

    @Operation(summary = "Update Article By Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Article successful update",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Article not found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArticleResponseDto::class)
                )]
            ),
        ]
    )
    fun change(articleId: Long, articleRequestDto: ArticleRequestDto): ResponseEntity<ArticleResponseDto>

    @Operation(summary = "Remove Article By Id")
    fun remove(articleId: Long)

}