package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entities.note.model.NoteId

data class NotePatchCommand(
    val noteId: NoteId,
    val url: String?,
    val title: String?,
    val content: String?
)
