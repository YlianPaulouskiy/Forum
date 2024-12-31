package ru.azati.forum.service.exception

class UserNotFoundException : Exception {

    constructor() : this("User is not found.")

    constructor(message: String?) : super(message)

}