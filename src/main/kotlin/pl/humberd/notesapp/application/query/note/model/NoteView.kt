package pl.humberd.notesapp.application.query.note.model

import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceMinimalView
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import java.util.*

data class NoteView(
    val id: NoteId,
    val author: UserMinimalView,
    val url: String?,
    val title: String,
    val content: String?,
    val tags: List<TagMinimalView>,
    val workspaces: List<WorkspaceMinimalView>,
    val createdAt: Calendar,
    val updatedAt: Calendar
)
