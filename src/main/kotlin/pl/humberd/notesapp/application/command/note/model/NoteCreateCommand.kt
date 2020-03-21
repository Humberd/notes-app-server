package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entity.user.model.UserId

data class NoteCreateCommand(
    val authorId: UserId,
    val url: String,
    val title: String,
    val content: String
)
