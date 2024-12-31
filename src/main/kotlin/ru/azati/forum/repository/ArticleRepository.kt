package ru.azati.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.azati.forum.model.Article

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {

    fun findByTitleContainingIgnoreCase(request: String): List<Article>

}