package ru.azati.forum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import ru.azati.forum.service.dto.CommentRequestDto
import ru.azati.forum.service.dto.CommentResponseDto

@Tag(
    name = "Comment Controller",
    description = "Controller responsible for handling requests related to comments."
)
interface CommentController {

    @Operation(summary = "Find Comments By Article Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Comments successful find",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(
                        schema = Schema(implementation = CommentResponseDto::class)
                    )
                )]
            )
        ]
    )
    fun findAllByArticle(articleId: Long, offset:Int?, limit:Int?): ResponseEntity<List<CommentResponseDto>>

    @Operation(summary = "Create Comment")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "Comment successful created",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = CommentResponseDto::class)
            )]
        )]
    )
    fun create(commentRequestDto: CommentRequestDto): ResponseEntity<CommentResponseDto>

    @Operation(summary = "Remove Comment By Id")
    fun remove(commentId: Long)

    @Operation(summary = "Update Comment By Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Comment successful update",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Article/User/Comment not found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CommentResponseDto::class)
                )]
            ),
        ]
    )
    fun change(commentId: Long, commentRequestDto: CommentRequestDto): ResponseEntity<CommentResponseDto>

}