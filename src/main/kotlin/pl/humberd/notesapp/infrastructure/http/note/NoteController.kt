package pl.humberd.notesapp.infrastructure.http.note

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.note.NoteCommandHandler
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.application.query.note.NoteQueryHandler
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.note.model.NoteView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.note.model.NoteCreateRequest
import pl.humberd.notesapp.infrastructure.http.note.model.NotePatchRequest

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteCommandHandler: NoteCommandHandler,
    private val noteQueryHandler: NoteQueryHandler
) {
    @PostMapping("")
    fun create(@RequestBody body: NoteCreateRequest): ResponseEntity<NoteView> {
        val note = noteCommandHandler.create(
            NoteCreateCommand(
                authorId = "2",
                title = body.title,
                url = body.url,
                content = body.content
            )
        )

        val view = noteQueryHandler.view(note.id)

        return ResponseBuilder.created(view)
    }

    @GetMapping("/{id}")
    fun read(@PathVariable("id") id: String): ResponseEntity<NoteView> {
        return ResponseBuilder.ok(noteQueryHandler.view(id))
    }

    @GetMapping("")
    fun readList(pageable: Pageable): ResponseEntity<NoteListView> {
        return ResponseBuilder.ok(noteQueryHandler.listView(NoteListFilter(pageable)))
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable("id") id: String,
        @RequestBody body: NotePatchRequest
    ): ResponseEntity<NoteView> {
        val note = noteCommandHandler.patch(
            NotePatchCommand(
                noteId = id,
                url = body.url,
                title = body.title,
                content = body.content
            )
        )

        val view = noteQueryHandler.view(note.id)

        return ResponseBuilder.updated(view)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: String
    ): ResponseEntity<Unit> {
        noteCommandHandler.delete(NoteDeleteCommand(id))

        return ResponseBuilder.notContent();
    }
}
