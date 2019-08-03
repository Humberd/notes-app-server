package pl.humberd.notesapp.notes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.JSON_OK

@RestController
@RequestMapping("notes")
@CrossOrigin
class NotesController(
    val service: NotesService
) {

    @PostMapping("")
    fun create(
        @RequestBody body: CreateNoteDto
    ): ResponseEntity<String> {
        service.create(body)

        return JSON_OK()
    }

    @PutMapping("{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody body: CreateNoteDto
    ): ResponseEntity<String> {
        service.update(id, body)

        return JSON_OK()
    }

    @GetMapping("")
    fun readAll(
        @RequestParam(defaultValue = "") tags: List<String>,
        @RequestParam(defaultValue = "") query: String
    ): ResponseEntity<List<NoteDto>> {
        val notes = service.readAll(tags, query)
            .map { note ->
                NoteDto(
                    note.id,
                    note.createdAt!!,
                    note.lastModifiedAt!!,
                    note.title,
                    note.content,
                    note.tags
                        .map { tag ->
                            NoteTagDto(tag.id, tag.displayName)
                        }
                        .sortedBy { tag -> tag.id }
                )
            }

        return ResponseEntity.ok(notes)
    }


}
