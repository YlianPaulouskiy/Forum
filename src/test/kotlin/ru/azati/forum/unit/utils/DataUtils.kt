package ru.azati.forum.unit.utils

import ru.azati.forum.model.Article
import ru.azati.forum.model.Comment
import ru.azati.forum.model.User
import ru.azati.forum.service.dto.*


const val userName:String = "User"
const val userEmail:String = "email@gmail.com"

const val articleTitle:String = "Web"
const val articleContent:String = "REACT is the main front framework."

const val commentContent:String = "Comment content"

class DataUtils {

    companion object {

        fun createUser(id: Long = 1L) = User(id, userName, userEmail)

        fun createUserResponse(id: Long = 1L) = UserResponseDto(id, userName, userEmail)

        fun createUserRequest()= UserRequestDto(userName, userEmail)

        fun createUserList() = listOf(createUser(), createUser(2L), createUser(3L))

        fun createUserResponseList() = listOf(createUserResponse(), createUserResponse(2L), createUserResponse(3L))

        fun createArticle(id: Long = 1L) = Article(id, articleTitle, articleContent)

        fun createArticleResponse(id: Long = 1L) = ArticleResponseDto(id, articleTitle, articleContent)

        fun createArticleRequest() = ArticleRequestDto(articleTitle, articleContent)

        fun createArticleList() = listOf(createArticle(), createArticle(2L), createArticle(3L))

        fun createArticleResponseList() = listOf(createArticleResponse(), createArticleResponse(2L), createArticleResponse(3L))

        fun createComment(id: Long = 1L) = Comment(id, commentContent, createUser(id), createArticle(id))

        fun createCommentResponse(id: Long = 1L) = CommentResponseDto(id, commentContent, id, id)

        fun createCommentRequest()= CommentRequestDto(commentContent, 1L, 1L)

        fun createCommentList() = listOf(createComment(), createComment(2L), createComment(3L))

        fun createCommentResponseList() = listOf(createCommentResponse(), createCommentResponse(2L), createCommentResponse(3L))

    }

}