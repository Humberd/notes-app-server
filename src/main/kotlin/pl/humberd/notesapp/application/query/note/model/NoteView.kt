package pl.humberd.notesapp.application.query.note.model

import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import java.util.*

data class NoteView(
    val id: NoteId,
    val author: UserMinimalView,
    val url: String,
    val title: String,
    val content: String,
    val tags: List<TagView>,
    val createAt: Calendar,
    val updatedAt: Calendar
)
