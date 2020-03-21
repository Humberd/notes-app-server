package pl.humberd.notesapp.application.query.note.model

import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entities.note.model.NoteId
import java.util.*

data class NoteView(
    val id: NoteId,
    val author: UserMinimalView,
    val url: String,
    val title: String,
    val content: String,
    val createAt: Calendar,
    val updatedAt: Calendar
)