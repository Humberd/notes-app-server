package pl.humberd.notesapp.infrastructure.http.note

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.note.NoteCommandHandler
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NoteIsAuthorCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.application.command.note_tag.NoteTagCommandHandler
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagCreateCommand
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagDeleteCommand
import pl.humberd.notesapp.application.query.note.NoteQueryHandler
import pl.humberd.notesapp.application.query.note.model.NoteView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.note.model.NoteCreateRequest
import pl.humberd.notesapp.infrastructure.http.note.model.NotePatchRequest
import pl.humberd.notesapp.infrastructure.http.note.model.NoteTagCreateRequest
import java.security.Principal
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@RestController
@ExperimentalContracts
@RequestMapping("/notes")
class NoteController(
    private val noteCommandHandler: NoteCommandHandler,
    private val noteQueryHandler: NoteQueryHandler,
    private val noteTagCommandHandler: NoteTagCommandHandler
) {
    @PostMapping("")
    fun create(
        @RequestBody @Valid body: NoteCreateRequest,
        principal: Principal
    ): ResponseEntity<NoteView> {
        val note = noteCommandHandler.createAndRefresh(
            NoteCreateCommand(
                authorId = principal.name,
                title = body.title,
                url = body.url,
                content = body.content
            )
        )

        val view = noteQueryHandler.view(note.id)

        return ResponseBuilder.created(view)
    }

    @GetMapping("/{id}")
    fun read(
        @PathVariable("id") id: String,
        principal: Principal
    ): ResponseEntity<NoteView> {
        noteCommandHandler.ensureIsAuthor(
            NoteIsAuthorCommand(
                noteId = id,
                userId = principal.name
            )
        )

        return ResponseBuilder.ok(noteQueryHandler.view(id))
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable("id") id: String,
        @RequestBody body: NotePatchRequest,
        principal: Principal
    ): ResponseEntity<NoteView> {
        noteCommandHandler.ensureIsAuthor(
            NoteIsAuthorCommand(
                noteId = id,
                userId = principal.name
            )
        )

        val note = noteCommandHandler.patchAndRefresh(
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
        @PathVariable("id") id: String,
        principal: Principal
    ): ResponseEntity<Unit> {
        noteCommandHandler.ensureIsAuthor(
            NoteIsAuthorCommand(
                noteId = id,
                userId = principal.name
            )
        )

        noteCommandHandler.delete(NoteDeleteCommand(id))

        return ResponseBuilder.noContent()
    }

    @PostMapping("/{id}/tags")
    fun createTag(
        @PathVariable("id") id: String,
        @RequestBody body: NoteTagCreateRequest,
        principal: Principal
    ): ResponseEntity<NoteView> {
        noteCommandHandler.ensureIsAuthor(
            NoteIsAuthorCommand(
                noteId = id,
                userId = principal.name
            )
        )

        noteTagCommandHandler.createAndRefresh(
            NoteTagCreateCommand(
                noteId = id,
                tagName = body.tagName,
                userId = principal.name
            )
        )

        return ResponseBuilder.ok(noteQueryHandler.view(id))
    }

    @DeleteMapping("/{noteId}/tags/{tagId}")
    fun deleteTag(
        @PathVariable("noteId") noteId: String,
        @PathVariable("tagId") tagId: String,
        principal: Principal
    ): ResponseEntity<Unit> {
        noteCommandHandler.ensureIsAuthor(
            NoteIsAuthorCommand(
                noteId = noteId,
                userId = principal.name
            )
        )

        noteTagCommandHandler.delete(
            NoteTagDeleteCommand(
                noteId = noteId,
                tagId = tagId
            )
        )

        return ResponseBuilder.noContent()
    }
}
