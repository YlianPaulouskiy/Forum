package ru.azati.forum.unit.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.azati.forum.controller.impl.ArticleControllerImpl
import ru.azati.forum.controller.impl.CommentControllerImpl
import ru.azati.forum.service.CommentService
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentResponseList
import kotlin.test.assertEquals

@WebMvcTest(CommentControllerImpl::class)
class CommentControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var commentService: CommentService

    val commentPath: String = "/api/v1/comments"

    @Test
    fun should200_whenFindByArticle_isOk() {
        val articleId = 1L
        val commentResponseList = createCommentResponseList()
        `when`(commentService.findAllByArticle(articleId))
            .thenReturn(commentResponseList)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(commentPath.plus("/$articleId"))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(commentResponseList)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }


    @Test
    fun should201_whenCreate_isOk() {
        val commentResponse = createCommentResponse()
        val commentRequest = createCommentRequest()
        `when`(commentService.create(commentRequest))
            .thenReturn(commentResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(commentPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequest))
        )
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(commentResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }

    @Test
    fun should200_whenChange_isOk() {
        val id = 1L
        val commentResponse = createCommentResponse()
        val commentRequest = createCommentRequest()
        `when`(commentService.change(id, commentRequest))
            .thenReturn(commentResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put(commentPath.plus("/$id"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequest))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(commentResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }



}