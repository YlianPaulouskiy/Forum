package ru.azati.forum.service.exception

class CommentNotFoundException : Exception {

    constructor() : this("Comment is not found.")

    constructor(message: String?) : super(message)
}