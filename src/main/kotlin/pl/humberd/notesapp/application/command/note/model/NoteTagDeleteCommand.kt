package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.tag.model.TagId

data class NoteTagDeleteCommand(
    val noteId: NoteId,
    val tagId: TagId
)
