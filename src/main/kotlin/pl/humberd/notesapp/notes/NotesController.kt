package pl.humberd.notesapp.notes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

        return ResponseEntity.ok("OK")
    }

    @GetMapping("")
    fun readAll(
        @RequestParam tags: List<String>,
        @RequestParam(defaultValue = "") query: String
    ): ResponseEntity<List<NoteDto>> {
        val notes = service.readAll(tags, query)
            .map { note ->
                NoteDto(
                    note.id,
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
