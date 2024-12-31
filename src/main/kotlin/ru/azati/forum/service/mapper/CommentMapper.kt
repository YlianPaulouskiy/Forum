package ru.azati.forum.service.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import ru.azati.forum.model.Comment
import ru.azati.forum.service.dto.CommentRequestDto
import ru.azati.forum.service.dto.CommentResponseDto

@Mapper(componentModel = "spring")
interface CommentMapper {

    @Mapping(target = "userId", source = "comment.user.id")
    @Mapping(target = "articleId", source = "comment.article.id")
    fun toCommentResponse(comment: Comment): CommentResponseDto

    @Mapping(target = "id", expression = "java(commentId != null ? commentId : null)")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "article", ignore = true)
    fun toComment(commentId: Long? = null, commentRequest: CommentRequestDto): Comment

}