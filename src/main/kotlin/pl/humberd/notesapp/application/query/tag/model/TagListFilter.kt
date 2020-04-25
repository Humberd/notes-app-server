package pl.humberd.notesapp.application.query.tag.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.user.model.UserId

sealed class TagListFilter(
    val pageable: Pageable
) {

    class ByNote(
        pageable: Pageable,
        val noteId: NoteId
    ) : TagListFilter(pageable)

    class ByUser(
        pageable: Pageable,
        val userId: UserId
    ) : TagListFilter(pageable)

}
