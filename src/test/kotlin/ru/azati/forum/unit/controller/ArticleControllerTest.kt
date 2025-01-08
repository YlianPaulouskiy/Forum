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
import ru.azati.forum.service.ArticleService
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleResponseList
import kotlin.test.assertEquals

@WebMvcTest(ArticleControllerImpl::class)
class ArticleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var articleService: ArticleService

    val articlePath: String = "/api/v1/articles"

    @Test
    fun should200_whenFindByTitle_isOk() {
        val articleResponseList = createArticleResponseList()
        val requestPair = "request" to "Web"
        `when`(articleService.findByTitle(requestPair.second))
            .thenReturn(articleResponseList)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(articlePath)
                .param(requestPair.first, requestPair.second)
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(articleResponseList)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }

    @Test
    fun should200_whenFindById_isOk() {
        val articleResponse = createArticleResponse()
        `when`(articleService.findById(anyLong()))
            .thenReturn(articleResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get(articlePath.plus("/1"))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(articleResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }


    @Test
    fun should201_whenCreate_isOk() {
        val articleResponse = createArticleResponse()
        val articleRequest = createArticleRequest()
        `when`(articleService.create(articleRequest))
            .thenReturn(articleResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post(articlePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest))
        )
            .andExpect(status().isCreated())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(articleResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }

    @Test
    fun should200_whenChange_isOk() {
        val id = 1L
        val articleResponse = createArticleResponse()
        val articleRequest = createArticleRequest()
        `when`(articleService.change(id, articleRequest))
            .thenReturn(articleResponse)

        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put(articlePath.plus("/$id"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleRequest))
        )
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

        val expectedAnswer = objectMapper.writeValueAsString(articleResponse)
        val resultAnswer = mvcResult.response.contentAsString
        assertEquals(expectedAnswer, resultAnswer)
    }



}