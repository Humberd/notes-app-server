package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId

data class NoteDeleteCommand(
    val noteId: NoteId
)
