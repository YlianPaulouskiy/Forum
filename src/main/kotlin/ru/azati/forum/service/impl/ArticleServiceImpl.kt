package ru.azati.forum.service.impl

import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import ru.azati.forum.repository.ArticleRepository
import ru.azati.forum.service.ArticleService
import ru.azati.forum.service.dto.ArticleRequestDto
import ru.azati.forum.service.dto.ArticleResponseDto
import ru.azati.forum.service.exception.ArticleNotFoundException
import ru.azati.forum.service.mapper.ArticleMapper

@Service
@Validated
@Transactional
class ArticleServiceImpl(
    val articleRepository: ArticleRepository,
    val articleMapper: ArticleMapper
) : ArticleService {

    override fun findById(articleId: Long): ArticleResponseDto {
        return articleMapper.toArticleResponse(
            articleRepository.findById(articleId)
                .orElseThrow(::ArticleNotFoundException)
        )
    }

    override fun findByTitle(request: String): List<ArticleResponseDto> {
        return articleRepository.findByTitleContainingIgnoreCase(request)
            .map(articleMapper::toArticleResponse)
            .toList()
    }

    override fun create(articleRequestDto: ArticleRequestDto): ArticleResponseDto {
        return articleMapper.toArticleResponse(
            articleRepository.save(
                articleMapper.toArticle(articleRequest = articleRequestDto)
            )
        )
    }

    override fun change(articleId: Long, articleRequestDto: ArticleRequestDto): ArticleResponseDto {
        return articleMapper.toArticleResponse(
            articleRepository.save(
                articleMapper.toArticle(articleId, articleRequestDto)
            )
        )
    }

    override fun remove(articleId: Long) {
        articleRepository.findById(articleId)
            .map(articleRepository::delete)
    }
}