package ru.azati.forum.unit.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest
import ru.azati.forum.repository.ArticleRepository
import ru.azati.forum.repository.CommentRepository
import ru.azati.forum.repository.UserRepository
import ru.azati.forum.service.exception.ArticleNotFoundException
import ru.azati.forum.service.exception.CommentNotFoundException
import ru.azati.forum.service.exception.UserNotFoundException
import ru.azati.forum.service.impl.CommentServiceImpl
import ru.azati.forum.service.mapper.CommentMapper
import ru.azati.forum.unit.utils.DataUtils.Companion.createArticle
import ru.azati.forum.unit.utils.DataUtils.Companion.createComment
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentList
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentRequest
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentResponse
import ru.azati.forum.unit.utils.DataUtils.Companion.createCommentResponseList
import ru.azati.forum.unit.utils.DataUtils.Companion.createUser
import java.util.*
import kotlin.test.assertEquals


@ExtendWith(MockitoExtension::class)
class CommentServiceImplTest {

    @Mock
    lateinit var commentMapper: CommentMapper

    @Mock
    lateinit var commentRepository: CommentRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var articleRepository: ArticleRepository

    @InjectMocks
    lateinit var commentServiceImpl: CommentServiceImpl

    @Test
    fun isOk_whenCreateComment() {
        val commentRequest = createCommentRequest()
        val commentResponse = createCommentResponse()
        val rightComment = createComment()

        `when`(commentMapper.toComment(null, commentRequest))
            .thenReturn(rightComment)
        `when`(userRepository.findById(commentRequest.userId))
            .thenReturn(Optional.of(createUser(commentRequest.userId)))
        `when`(articleRepository.findById(commentRequest.articleId))
            .thenReturn(Optional.of(createArticle(commentRequest.articleId)))

        `when`(commentMapper.toCommentResponse(rightComment))
            .thenReturn(commentResponse)
        `when`(commentRepository.save(rightComment))
            .thenReturn(rightComment)

        val savedComment = commentServiceImpl.create(commentRequest)

        verify(commentMapper).toCommentResponse(rightComment)
        verify(commentMapper).toComment(null, commentRequest)
        verify(commentRepository).save(rightComment)

        assertEquals(rightComment.id, savedComment.id)
        assertEquals(rightComment.content, savedComment.content)
        assertEquals(rightComment.user.id, rightComment.user.id)
        assertEquals(rightComment.article.id, rightComment.article.id)
    }

    @Test
    fun isUserNotFound_whenCreateComment() {
        val commentRequest = createCommentRequest()
        val rightComment = createComment()

        `when`(commentMapper.toComment(null, commentRequest))
            .thenReturn(rightComment)
        `when`(userRepository.findById(commentRequest.userId))
            .thenReturn(Optional.empty())

        assertThrows<UserNotFoundException> {
            commentServiceImpl.create(commentRequest)
        }

    }

    @Test
    fun isArticleNotFound_whenCreateComment() {
        val commentRequest = createCommentRequest()
        val rightComment = createComment()

        `when`(commentMapper.toComment(null, commentRequest))
            .thenReturn(rightComment)
        `when`(userRepository.findById(commentRequest.userId))
            .thenReturn(Optional.of(createUser(commentRequest.userId)))
        `when`(articleRepository.findById(commentRequest.articleId))
            .thenReturn(Optional.empty())

        assertThrows<ArticleNotFoundException> {
            commentServiceImpl.create(commentRequest)
        }

    }



    @Test
    fun isOk_whenChange() {
        val comment = createComment()
        val commentResponse = createCommentResponse()
        val commentRequest = createCommentRequest()

        `when`(commentRepository.findById(anyLong()))
            .thenReturn(Optional.of(comment))


        `when`(commentMapper.toCommentResponse(comment))
            .thenReturn(commentResponse)
        `when`(commentRepository.save(comment))
            .thenReturn(comment)

        val changedComment = commentServiceImpl.change(1L, commentRequest)

        verify(commentMapper).toCommentResponse(comment)
        verify(commentRepository).save(comment)

        assertEquals(comment.id, changedComment.id)
        assertEquals(comment.content, changedComment.content)
        assertEquals(comment.user.id, changedComment.userId)
        assertEquals(comment.article.id, changedComment.articleId)

    }

    @Test
    fun isCommentNotFoundException_whenChangeCommentNotExist() {
        `when`(commentRepository.findById(anyLong()))
            .thenReturn(Optional.empty())
        assertThrows<CommentNotFoundException> {
            commentServiceImpl.change(1L, createCommentRequest())
        }
    }

    @Test
    fun isOk_whenFindAllByArticle() {

        val commentList = createCommentList()
        val commentResponseList = createCommentResponseList()

        `when`(commentRepository.findByArticleId(1L, PageRequest.of(1, 10)))
            .thenReturn(commentList)
        for (i in commentList.indices) {
            `when`(commentMapper.toCommentResponse(commentList[i]))
                .thenReturn(commentResponseList[i])
        }

        val responseFromService = commentServiceImpl.findAllByArticle(1L, 1, 10)

        assertEquals(commentResponseList.size, responseFromService.size)
        for (i in responseFromService.indices) {
            assertEquals(commentResponseList[i].id, responseFromService[i].id)
            assertEquals(commentResponseList[i].content, responseFromService[i].content)
            assertEquals(commentResponseList[i].userId, responseFromService[i].userId)
            assertEquals(commentResponseList[i].articleId, responseFromService[i].articleId)
        }
    }


}