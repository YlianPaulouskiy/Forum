package ru.azati.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "articles")
open class Article(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long?,

    @Column(
        name = "title", nullable = false,
        columnDefinition = "VARCHAR(50)"
    )
    open val title: String,

    @Column(
        name = "content", nullable = false,
        columnDefinition = "TEXT"
    )
    open val content: String

) {
}