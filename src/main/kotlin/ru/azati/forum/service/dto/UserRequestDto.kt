package ru.azati.forum.service.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRequestDto(

    @field:NotBlank
    val username:String,

    @field:Email
    val email:String

)