package ru.azati.forum.controller.impl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.azati.forum.controller.CommentController
import ru.azati.forum.service.CommentService
import ru.azati.forum.service.dto.CommentRequestDto
import ru.azati.forum.service.dto.CommentResponseDto

@RestController
@RequestMapping("/api/v1/comments")
class CommentControllerImpl(
    val commentService: CommentService
) : CommentController {


    @GetMapping("/{id}")
    override fun findAllByArticle(
        @PathVariable("id") articleId: Long,
        @RequestParam(required = false) offset: Int?,
        @RequestParam(required = false) limit: Int?
    ): ResponseEntity<List<CommentResponseDto>> {
        return ResponseEntity.ok(
            commentService.findAllByArticle(
                articleId, offset ?: 1, limit ?: 10
            )
        )
    }

    @PostMapping
    override fun create(@RequestBody commentRequestDto: CommentRequestDto): ResponseEntity<CommentResponseDto> {
        return ResponseEntity(
            commentService.create(commentRequestDto),
            HttpStatus.CREATED
        )
    }

    @DeleteMapping
    override fun remove(@RequestParam("id") commentId: Long) {
        commentService.remove(commentId)
    }

    @PutMapping("/{id}")
    override fun change(
        @PathVariable("id") commentId: Long,
        @RequestBody commentRequestDto: CommentRequestDto
    ): ResponseEntity<CommentResponseDto> {
        return ResponseEntity.ok(
            commentService.change(commentId, commentRequestDto)
        )
    }


}