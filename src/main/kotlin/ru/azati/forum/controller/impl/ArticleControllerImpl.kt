package ru.azati.forum.controller.impl

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.azati.forum.controller.ArticleController
import ru.azati.forum.service.ArticleService
import ru.azati.forum.service.dto.ArticleRequestDto
import ru.azati.forum.service.dto.ArticleResponseDto

@RestController
@RequestMapping("/api/v1/articles")
class ArticleControllerImpl(
    val articleService: ArticleService
) : ArticleController {

    @GetMapping("/{id}")
    override fun findById(@PathVariable("id") articleId: Long): ResponseEntity<ArticleResponseDto> {
        return ResponseEntity.ok(
            articleService.findById(articleId)
        )
    }

    @GetMapping
    override fun findByTitle(@RequestParam request: String): ResponseEntity<List<ArticleResponseDto>> {
        return ResponseEntity.ok(
            articleService.findByTitle(request)
        )
    }

    @PostMapping
    override fun create(@RequestBody articleRequestDto: ArticleRequestDto): ResponseEntity<ArticleResponseDto> {
        return ResponseEntity(
            articleService.create(articleRequestDto),
            HttpStatus.CREATED
        )
    }

    @PutMapping("/{id}")
    override fun change(
        @PathVariable("id") articleId: Long,
        @RequestBody articleRequestDto: ArticleRequestDto
    ): ResponseEntity<ArticleResponseDto> {
        return ResponseEntity.ok(
            articleService.change(articleId, articleRequestDto)
        )
    }

    @DeleteMapping
    override fun remove(@RequestParam("id") articleId: Long) {
        articleService.remove(articleId)
    }

}