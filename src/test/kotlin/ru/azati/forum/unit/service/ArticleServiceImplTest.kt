package ru.azati.forum.unit.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.azati.forum.repository.ArticleRepository
import ru.azati.forum.service.exception.ArticleNotFoundException
import ru.azati.forum.service.impl.ArticleServiceImpl
import ru.azati.forum.service.mapper.ArticleMapper
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticle
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleList
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticleResponseList
import java.util.*
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class ArticleServiceImplTest {

    @Mock
    lateinit var articleMapper: ArticleMapper

    @Mock
    lateinit var articleRepository: ArticleRepository

    @InjectMocks
    lateinit var articleService: ArticleServiceImpl

    @Test
    fun isOk_whenCreateArticle() {
        val articleRequest = createArticleRequest()
        val articleResponse = createArticleResponse()
        val rightArticle = createArticle()

        `when`(articleMapper.toArticle(null, articleRequest))
            .thenReturn(rightArticle)
        `when`(articleMapper.toArticleResponse(rightArticle))
            .thenReturn(articleResponse)
        `when`(articleRepository.save(rightArticle))
            .thenReturn(rightArticle)

        val savedUser = articleService.create(articleRequest)

        verify(articleMapper).toArticleResponse(rightArticle)
        verify(articleMapper).toArticle(null, articleRequest)
        verify(articleRepository).save(rightArticle)

        assertEquals(rightArticle.id, savedUser.id)
        assertEquals(rightArticle.title, savedUser.title)
        assertEquals(rightArticle.content, rightArticle.content)
    }



    @ParameterizedTest
    @ValueSource(longs = [1L,2L,3L])
    fun isOk_whenFindById(id: Long) {
        val rightArticle = createArticle()
        `when`(articleRepository.findById(id))
            .thenReturn(Optional.of(rightArticle))
        `when`(articleMapper.toArticleResponse(rightArticle))
            .thenReturn(createArticleResponse())

        val articleResponse = articleService.findById(id)

        assertEquals(rightArticle.id, articleResponse.id)
        assertEquals(rightArticle.title, articleResponse.title)
        assertEquals(rightArticle.content, rightArticle.content)
    }

    @Test
    fun isException_whenFindById() {
        `when`(articleRepository.findById(anyLong()))
            .thenReturn(Optional.empty())

        assertThrows<ArticleNotFoundException> {
            articleService.findById(anyLong())
        }
    }

    @Test
    fun isOk_whenChange() {
        val article = createArticle()
        val articleResponse = createArticleResponse()
        val articleRequest = createArticleRequest()
        `when`(articleMapper.toArticleResponse(article))
            .thenReturn(articleResponse)
        `when`(articleMapper.toArticle(1L, articleRequest))
            .thenReturn(article)
        `when`(articleRepository.save(article))
            .thenReturn(article)

        val changedUser = articleService.change(1L, articleRequest)

        verify(articleMapper).toArticleResponse(article)
        verify(articleMapper).toArticle(1L, articleRequest)
        verify(articleRepository).save(article)

        assertEquals(article.id, changedUser.id)
        assertEquals(article.title, changedUser.title)
        assertEquals(article.content, changedUser.content)

    }


    @Test
    fun isOk_whenFindByTitle() {

        val articleList = createArticleList()
        val articleResponseList = createArticleResponseList()

        `when`(articleRepository.findByTitleContainingIgnoreCase(anyString()))
            .thenReturn(articleList)
        for(i in articleList.indices)  {
            `when`(articleMapper.toArticleResponse(articleList[i]))
                .thenReturn(articleResponseList[i])
        }

        val responseFromService = articleService.findByTitle(articleList[0].title)

        assertEquals(articleResponseList.size, responseFromService.size)
        for (i in responseFromService.indices) {
            assertEquals(articleResponseList[i].id, responseFromService[i].id)
            assertEquals(articleResponseList[i].title, responseFromService[i].title)
            assertEquals(articleResponseList[i].content, responseFromService[i].content)
        }
    }


    @Test
    fun isOk_whenRemove() {
        `when`(articleRepository.findById(anyLong()))
            .thenReturn(Optional.of(createArticle()))

        assertDoesNotThrow {
            articleService.remove(anyLong())
        }
    }

}