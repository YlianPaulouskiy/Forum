package ru.azati.forum.service.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.azati.forum.model.User
import ru.azati.forum.service.dto.UserRequestDto
import ru.azati.forum.service.dto.UserResponseDto

@Mapper(componentModel = "spring")
interface UserMapper {

    fun toUserResponse(user: User): UserResponseDto

    @Mapping(target = "id", expression = "java(userId != null ? userId : null)")
    fun toUser(userId:Long? = null, userRequest: UserRequestDto): User

}