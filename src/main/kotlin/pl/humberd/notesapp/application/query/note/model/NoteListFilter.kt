package pl.humberd.notesapp.application.query.note.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.user.model.UserId

data class NoteListFilter(
    val pageable: Pageable,
    val authorId: UserId
)
