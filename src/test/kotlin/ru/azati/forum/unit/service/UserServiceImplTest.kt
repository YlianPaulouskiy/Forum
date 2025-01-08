package ru.azati.forum.unit.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.azati.forum.repository.UserRepository
import ru.azati.forum.service.exception.UserNotFoundException
import ru.azati.forum.service.impl.UserServiceImpl
import ru.azati.forum.service.mapper.UserMapper
import ru.azati.forum.unit.utils.DataUtils.Companion.createUser
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserList
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserResponseList
import java.util.*
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    @Mock
    lateinit var userMapper: UserMapper

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userServiceImpl: UserServiceImpl

    @Test
    fun isOk_whenCreateUser() {
        val userRequest = createUserRequest()
        val userResponse = createUserResponse()
        val rightUser = createUser()

        `when`(userMapper.toUser(null, userRequest))
            .thenReturn(rightUser)
        `when`(userMapper.toUserResponse(rightUser))
            .thenReturn(userResponse)
        `when`(userRepository.save(rightUser))
            .thenReturn(rightUser)

        val savedUser = userServiceImpl.create(userRequest)

        verify(userMapper).toUserResponse(rightUser)
        verify(userMapper).toUser(null, userRequest)
        verify(userRepository).save(rightUser)

        assertEquals(rightUser.id, savedUser.id)
        assertEquals(rightUser.username, savedUser.username)
        assertEquals(rightUser.email, rightUser.email)
    }



    @ParameterizedTest
    @ValueSource(longs = [1L,2L,3L])
    fun isOk_whenFindById(id: Long) {
        val rightUser = createUser()
        `when`(userRepository.findById(id))
            .thenReturn(Optional.of(rightUser))
        `when`(userMapper.toUserResponse(rightUser))
            .thenReturn(createUserResponse())

        val userResponse = userServiceImpl.findById(id)

        assertEquals(rightUser.id, userResponse.id)
        assertEquals(rightUser.username, userResponse.username)
        assertEquals(rightUser.email, rightUser.email)
    }

    @Test
    fun isException_whenFindById() {
        `when`(userRepository.findById(anyLong()))
            .thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> {
            userServiceImpl.findById(anyLong())
        }
    }

    @Test
    fun isOk_whenChange() {
        val user = createUser()
        val userResponse = createUserResponse()
        val userRequest = createUserRequest()
        `when`(userMapper.toUserResponse(user))
            .thenReturn(userResponse)
        `when`(userMapper.toUser(1L, userRequest))
            .thenReturn(user)
        `when`(userRepository.save(user))
            .thenReturn(user)

        val changedUser = userServiceImpl.change(1L, userRequest)

        verify(userMapper).toUserResponse(user)
        verify(userMapper).toUser(1L, userRequest)
        verify(userRepository).save(user)

        assertEquals(user.id, changedUser.id)
        assertEquals(user.username, changedUser.username)
        assertEquals(user.email, changedUser.email)

    }


    @Test
    fun isOk_whenFindAll() {

        val userList = createUserList()
        val userResponseList = createUserResponseList()

        `when`(userRepository.findAll())
            .thenReturn(userList)
        for(i in userList.indices)  {
            `when`(userMapper.toUserResponse(userList[i]))
                .thenReturn(userResponseList[i])
        }

        val responseFromService = userServiceImpl.findAll()

        assertEquals(userResponseList.size, responseFromService.size)
        for (i in responseFromService.indices) {
            assertEquals(userResponseList[i].id, responseFromService[i].id)
            assertEquals(userResponseList[i].username, responseFromService[i].username)
            assertEquals(userResponseList[i].email, responseFromService[i].email)
        }
    }


}