package ru.azati.forum.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import ru.azati.forum.service.dto.UserRequestDto
import ru.azati.forum.service.dto.UserResponseDto

@Tag(
    name = "User Controller",
    description = "Controller responsible for handling requests related to users."
)
interface UserController {


    @Operation(summary = "Create User")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "201",
            description = "User successful created",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = UserResponseDto::class)
            )]
        )]
    )
    fun create(userRequestDto: UserRequestDto): ResponseEntity<UserResponseDto>

    @Operation(summary = "Find User By Id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User successful find",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User not found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
        ]
    )
    fun findById(userId: Long): ResponseEntity<UserResponseDto>

    @Operation(summary = "Remove User By Id")
    fun remove(userId: Long)

    @Operation(summary = "Update User")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User successful update",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User not found",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
        ]
    )
    fun change(userId: Long, userRequestDto: UserRequestDto): ResponseEntity<UserResponseDto>

    @Operation(summary = "Get All Users")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User successful find",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(
                        schema = Schema(implementation = UserResponseDto::class)
                    )
                )]
            )
        ]
    )
    fun findAll(): ResponseEntity<List<UserResponseDto>>

}