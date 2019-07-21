package pl.humberd.notesapp.notes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("notes")
class NotesController(
    val service: NotesService
) {

    @PostMapping("")
    fun create(
        @RequestBody body: CreateNoteDto
    ): ResponseEntity<String> {
        service.create(body)

        return ResponseEntity.ok("OK")
    }

    @GetMapping("")
    fun readAll(): ResponseEntity.BodyBuilder {
        return ResponseEntity.ok()
    }


}
