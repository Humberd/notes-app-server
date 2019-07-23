package pl.humberd.notesapp.tags

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tags")
@CrossOrigin
class TagsController(
    private val tagsService: TagsService
) {
    @GetMapping("")
    fun readAll(): ResponseEntity<List<TagDto>> {
        val tags = tagsService.readAll()

        return ResponseEntity.ok(TagDto.fromTags(tags))
    }
}
