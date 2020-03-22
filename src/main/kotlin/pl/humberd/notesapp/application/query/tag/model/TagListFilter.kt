package pl.humberd.notesapp.application.query.tag.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.note.model.NoteId

data class TagListFilter(
    val pageable: Pageable,
    val noteId: NoteId
)
