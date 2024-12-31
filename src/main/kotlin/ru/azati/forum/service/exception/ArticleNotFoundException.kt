package ru.azati.forum.service.exception

class ArticleNotFoundException : Exception {

    constructor(): this("Article is not found.")

    constructor(message:String): super(message)

}