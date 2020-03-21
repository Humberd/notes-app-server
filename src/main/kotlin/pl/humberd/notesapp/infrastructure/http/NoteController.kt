package pl.humberd.notesapp.infrastructure.http

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.note.NoteCommandHandler
import pl.humberd.notesapp.application.query.note.NoteQueryHandler
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.note.model.NoteView

@RestController
@RequestMapping("/notes")
class NoteController(
    private val noteCommandHandler: NoteCommandHandler,
    private val noteQueryHandler: NoteQueryHandler
) {
    @GetMapping("")
    fun readList(pageable: Pageable): ResponseEntity<NoteListView> {
        return ResponseEntity.ok(noteQueryHandler.listView(NoteListFilter(pageable)))
    }

    @GetMapping("/{id}")
    fun read(@PathVariable("id") id: String): ResponseEntity<NoteView> {
        return ResponseEntity.ok(noteQueryHandler.view(id))
    }
}
