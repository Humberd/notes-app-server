package pl.humberd.notesapp.infrastructure.http.user

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.query.note.NoteQueryHandler
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/users")
@RestController
class UserHttpController(
    private val userQueryHandler: UserQueryHandler,
    private val noteQueryHandler: NoteQueryHandler
) {

    @GetMapping("/{userId}")
    fun readProfile(
        @PathVariable("userId") userId: String
    ): ResponseEntity<UserView> {
        val view = userQueryHandler.view(userId)

        return ResponseBuilder.ok(view)
    }

    @GetMapping("/{userId}/notes")
    fun readNotesList(
        pageable: Pageable,
        @PathVariable("userId") userId: String,
        @RequestParam(value = "query", required = false, defaultValue = "") query: String,
        @RequestParam(value = "url", required = false, defaultValue = "") url: String
    ): ResponseEntity<NoteListView> {
        val listQuery: NoteListFilter = NoteListFilter.Compound(
            pageable = pageable,
            authorId = userId,
            query = query,
            url = url
        )

        return ResponseBuilder.ok(noteQueryHandler.listView(listQuery))
    }

}
