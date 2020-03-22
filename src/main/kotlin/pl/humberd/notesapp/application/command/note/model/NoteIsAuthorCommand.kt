package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.user.model.UserId

data class NoteIsAuthorCommand(
    val noteId: NoteId,
    val userId: UserId
)
