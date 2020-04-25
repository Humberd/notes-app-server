package pl.humberd.notesapp.application.query.workspace.model

import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId
import java.util.*

data class WorkspaceView(
    val id: WorkspaceId,
    val name: String,
    val createdAt: Calendar,
    val updatedAt: Calendar
)
