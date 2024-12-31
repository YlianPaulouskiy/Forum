package ru.azati.forum.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.azati.forum.model.User

@Repository
interface UserRepository: JpaRepository<User, Long> {



}