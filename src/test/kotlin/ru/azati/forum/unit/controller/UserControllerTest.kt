package ru.azati.forum.unit.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.azati.forum.controller.impl.UserControllerImpl
import ru.azati.forum.service.UserService
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createUserResponseList
import kotlin.test.assertEquals

@WebMvcTest(UserControllerImpl::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var userService: UserService

    val userPath: String = "/api/v1/users"

    @Test
    fun should200_whenFindAll_isOk() {
        val userResponseList = createUserResponseList()
        `when`(userService.findAll()).thenReturn(userResponseList)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(userPath)
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(userResponseList)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }

    @Test
    fun should200_whenFindById_isOk() {
        val userResponse = createUserResponse()
        `when`(userService.findById(anyLong()))
            .thenReturn(userResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(userPath.plus("/1"))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(userResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }


    @Test
    fun should201_whenCreate_isOk() {
        val userResponse = createUserResponse()
        val userRequest = createUserRequest()
        `when`(userService.create(userRequest))
            .thenReturn(userResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(userPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(userResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }

    @Test
    fun should200_whenChange_isOk() {
        val id = 1L
        val userResponse = createUserResponse()
        val userRequest = createUserRequest()
        `when`(userService.change(id, userRequest))
            .thenReturn(userResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put(userPath.plus("/$id"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(userResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }



}