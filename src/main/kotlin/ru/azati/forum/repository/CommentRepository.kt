package ru.azati.forum.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.azati.forum.model.Comment

@Repository
interface CommentRepository: JpaRepository<Comment, Long> {

    fun findByArticleId(articleId:Long, pageable: Pageable): List<Comment>

}