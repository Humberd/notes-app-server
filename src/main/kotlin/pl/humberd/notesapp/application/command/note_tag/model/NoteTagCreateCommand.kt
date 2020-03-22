package pl.humberd.notesapp.application.command.note_tag.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.tag.model.TagId
import pl.humberd.notesapp.domain.entity.user.model.UserId

sealed class NoteTagCreateCommand {
    data class ExistingTag(
        val noteId: NoteId,
        val tagId: TagId
    ) : NoteTagCreateCommand()

    data class NotExistingTag(
        val noteId: NoteId,
        val userId: UserId,
        val tagName: String
    ) : NoteTagCreateCommand()
}
