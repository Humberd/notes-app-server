package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import java.util.*

data class NotePatchCommand(
    val noteId: NoteId,
    val title: String?,
    val url: Optional<String>?,
    val content: Optional<String>?
)
