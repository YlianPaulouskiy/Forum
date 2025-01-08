package ru.azati.forum.service.impl

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.azati.forum.service.dto.UserRequestDto
import ru.azati.forum.service.dto.UserResponseDto
import ru.azati.forum.repository.UserRepository
import ru.azati.forum.service.UserService
import ru.azati.forum.service.exception.UserNotFoundException
import ru.azati.forum.service.mapper.UserMapper

@Service
@Validated
@Transactional
class UserServiceImpl(
    val userRepository: UserRepository,
    val userMapper: UserMapper
) : UserService {

    override fun create(userRequestDto: UserRequestDto): UserResponseDto {
        return userMapper.toUserResponse(
            userRepository.save(
                userMapper.toUser(userRequest = userRequestDto)
            )
        )
    }

    override fun findById(userId: Long): UserResponseDto {
        return userRepository.findById(userId)
            .map(userMapper::toUserResponse)
            .orElseThrow(::UserNotFoundException)
    }

    override fun remove(userId: Long) {
        userRepository.findById(userId)
            .ifPresent(userRepository::delete)
    }

    override fun change(userId: Long, userRequestDto: UserRequestDto): UserResponseDto {
        return userMapper.toUserResponse(
            userRepository.save(
                userMapper.toUser(userId, userRequestDto)
            )
        )
    }

    override fun findAll(): List<UserResponseDto> {
        return userRepository.findAll()
            .map(userMapper::toUserResponse)
            .toList()
    }

}