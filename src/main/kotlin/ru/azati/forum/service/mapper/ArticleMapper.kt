package ru.azati.forum.service.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.azati.forum.model.Article
import ru.azati.forum.service.dto.ArticleRequestDto
import ru.azati.forum.service.dto.ArticleResponseDto

@Mapper(componentModel = "spring")
interface ArticleMapper {

    fun toArticleResponse(article: Article): ArticleResponseDto

    @Mapping(target = "id", expression = "java(articleId != null ? articleId : null)")
    fun toArticle(articleId: Long? = null, articleRequest: ArticleRequestDto): Article

}