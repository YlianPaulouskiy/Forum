package ru.azati.forum.controller.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleConstraintException(ex: Exception): ResponseEntity<ExceptionInfo> {
        val exceptionInfo = ExceptionInfo(
            HttpStatus.BAD_REQUEST.value(),
            ex.message.toString()
        )
        return ResponseEntity(exceptionInfo, HttpStatus.BAD_REQUEST)
    }

}