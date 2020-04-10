package pl.humberd.notesapp.application.command.note.model

import pl.humberd.notesapp.application.common.model.NameModel
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.user.model.UserId
import java.util.*

data class NotePatchCommand(
    val userId: UserId,
    val noteId: NoteId,
    val title: String?,
    val url: Optional<String>?,
    val content: Optional<String>?,
    val tags: Collection<NameModel>?
)
