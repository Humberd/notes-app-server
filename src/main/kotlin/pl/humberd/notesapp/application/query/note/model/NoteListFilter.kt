package pl.humberd.notesapp.application.query.note.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.user.model.UserId

sealed class NoteListFilter(
    val pageable: Pageable
) {
    class ByAuthor(
        pageable: Pageable,
        val authorId: UserId
    ) : NoteListFilter(pageable)

    class ByUrl(
        pageable: Pageable,
        val authorId: UserId,
        val url: String
    ) : NoteListFilter(pageable)

    class ByQuery(
        pageable: Pageable,
        val authorId: UserId,
        val query: String
    ) : NoteListFilter(pageable)
}
