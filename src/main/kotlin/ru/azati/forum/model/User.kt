package ru.azati.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
open class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long?,

    @Column(
        name = "username", nullable = false,
        unique = true, columnDefinition = "VARCHAR(30)"
    )
    open var username: String,

    @Column(
        name = "email", nullable = false,
        unique = true, columnDefinition = "VARCHAR(50)"
    )
    open var email: String
) {

}