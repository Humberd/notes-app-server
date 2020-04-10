package pl.humberd.notesapp.application.query.note.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.user.model.UserId

sealed class NoteListFilter(
    val pageable: Pageable
) {
    class Compound(
        pageable: Pageable,
        val authorId: UserId?,
        val url: String?,
        val query: String?
    ): NoteListFilter(pageable)
}
