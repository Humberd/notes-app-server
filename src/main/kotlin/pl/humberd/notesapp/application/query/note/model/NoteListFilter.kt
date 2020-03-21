package pl.humberd.notesapp.application.query.note.model

import org.springframework.data.domain.Pageable

data class NoteListFilter(
    val pageable: Pageable
)
