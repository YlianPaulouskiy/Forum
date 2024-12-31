package ru.azati.forum.service.impl

import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.azati.forum.model.Article
import ru.azati.forum.model.User
import ru.azati.forum.repository.ArticleRepository
import ru.azati.forum.repository.CommentRepository
import ru.azati.forum.repository.UserRepository
import ru.azati.forum.service.CommentService
import ru.azati.forum.service.dto.CommentRequestDto
import ru.azati.forum.service.dto.CommentResponseDto
import ru.azati.forum.service.exception.ArticleNotFoundException
import ru.azati.forum.service.exception.CommentNotFoundException
import ru.azati.forum.service.exception.UserNotFoundException
import ru.azati.forum.service.mapper.CommentMapper

@Service
@Validated
@Transactional
class CommentServiceImpl(
    val commentRepository: CommentRepository,
    val articleRepository: ArticleRepository,
    val userRepository: UserRepository,
    val commentMapper: CommentMapper
) : CommentService {


    override fun findAllByArticle(
        articleId: Long,
        offset: Int,
        limit: Int
    ): List<CommentResponseDto> {
        return commentRepository.findByArticleId(articleId, PageRequest.of(offset, limit))
            .map(commentMapper::toCommentResponse)
            .toList()
    }

    override fun create(commentRequestDto: CommentRequestDto): CommentResponseDto {
        val comment = commentMapper.toComment(commentRequest = commentRequestDto)

        comment.user = getUser(commentRequestDto.userId)
        comment.article = getArticle(commentRequestDto.articleId)

        return commentMapper.toCommentResponse(
            commentRepository.save(comment)
        )
    }

    override fun remove(commentId: Long) {
        commentRepository.findById(commentId)
            .map(commentRepository::delete)
    }

    override fun change(commentId: Long, commentRequestDto: CommentRequestDto): CommentResponseDto {
        val comment = commentRepository.findById(commentId)
            .orElseThrow(::CommentNotFoundException)

        comment.content = commentRequestDto.content
        comment.user = if (comment.user.id != commentRequestDto.userId)
            getUser(commentRequestDto.userId) else comment.user
        comment.article = if (comment.article.id != commentRequestDto.articleId)
            getArticle(commentRequestDto.userId) else comment.article

        return commentMapper.toCommentResponse(
            commentRepository.save(comment)
        )
    }

      private inline fun getUser(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow(::UserNotFoundException)
    }

    private inline fun getArticle(articleId: Long): Article {
        return articleRepository.findById(articleId)
            .orElseThrow(::ArticleNotFoundException)
    }

}