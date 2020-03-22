package pl.humberd.notesapp.application.command.note_tag.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.user.model.UserId

data class NoteTagCreateCommand(
    val noteId: NoteId,
    val userId: UserId,
    val tagName: String
)
