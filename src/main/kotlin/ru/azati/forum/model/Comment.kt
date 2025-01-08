package ru.azati.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "comments")
open class Comment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long?,

    @Column(
        name = "content", nullable = false,
        columnDefinition = "VARCHAR"
    )
    open var content: String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    open var user: User,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    open var article: Article

) {
}