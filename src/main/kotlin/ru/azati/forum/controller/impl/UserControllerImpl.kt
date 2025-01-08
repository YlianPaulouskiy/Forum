package ru.azati.forum.controller.impl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.azati.forum.controller.UserController
import ru.azati.forum.service.UserService
import ru.azati.forum.service.dto.UserRequestDto
import ru.azati.forum.service.dto.UserResponseDto

@RestController
@RequestMapping("/api/v1/users")
class UserControllerImpl(
    val userService: UserService
) : UserController {

    @PostMapping
    override fun create(@RequestBody userRequestDto: UserRequestDto): ResponseEntity<UserResponseDto> {
        return ResponseEntity<UserResponseDto>(
            userService.create(userRequestDto),
            HttpStatus.CREATED
        )
    }

    @GetMapping("/{id}")
    override fun findById(@PathVariable("id") userId: Long): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(
            userService.findById(userId)
        )
    }

    @DeleteMapping
    override fun remove(@RequestParam("id") userId: Long) {
        userService.remove(userId)
    }


    @PutMapping("/{id}")
    override fun change(
        @PathVariable("id") userId: Long,
        @RequestBody userRequestDto: UserRequestDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(
            userService.change(userId, userRequestDto)
        )
    }

    @GetMapping
    override fun findAll(): ResponseEntity<List<UserResponseDto>> {
        return ResponseEntity.ok(
            userService.findAll()
        )
    }

}