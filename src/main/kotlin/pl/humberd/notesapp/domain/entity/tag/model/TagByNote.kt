package pl.humberd.notesapp.domain.entity.tag.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId

data class TagByNote(
    val tag: Tag,
    val noteId: NoteId
)
