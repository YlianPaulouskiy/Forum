package ru.azati.forum.service

import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import ru.azati.forum.service.dto.UserRequestDto
import ru.azati.forum.service.dto.UserResponseDto


interface UserService {

    fun create(@Valid userRequestDto: UserRequestDto): UserResponseDto

    fun findById(@Positive userId: Long): UserResponseDto

    fun remove(@Positive userId: Long)

    fun change(@Positive userId: Long, @Valid userRequestDto: UserRequestDto): UserResponseDto

    fun findAll():List<UserResponseDto>

}