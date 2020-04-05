package pl.humberd.notesapp.infrastructure.http.my

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.query.note.NoteQueryHandler
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import java.security.Principal
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/my/")
@RestController
class MyHttpController(
    private val userQueryHandler: UserQueryHandler,
    private val noteQueryHandler: NoteQueryHandler
) {

    @GetMapping("/profile")
    fun readUserProfile(
        principal: Principal
    ): ResponseEntity<UserView> {
        val view = userQueryHandler.view(principal.name)

        return ResponseBuilder.ok(view)
    }

    @GetMapping("/notes")
    fun readNotesList(
        pageable: Pageable,
        principal: Principal,
        @RequestParam("query") query: String?
    ): ResponseEntity<NoteListView> {
        val listQuery: NoteListFilter = if (query === null || query.isBlank()) {
            NoteListFilter.Regular(
                pageable = pageable,
                authorId = principal.name
            )
        } else {
            NoteListFilter.ByQuery(
                pageable = pageable,
                authorId = principal.name,
                query = query
            )
        }

        return ResponseBuilder.ok(noteQueryHandler.listView(listQuery))
    }
}
