package pl.humberd.notesapp.application.query.note.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.user.model.UserId

sealed class NoteListFilter(
    val pageable: Pageable,
    val authorId: UserId
) {
    class Regular(
        pageable: Pageable,
        authorId: UserId
    ) : NoteListFilter(pageable, authorId)

    class ByQuery(
        pageable: Pageable,
        authorId: UserId,
        val query: String
    ) : NoteListFilter(pageable, authorId)
}
