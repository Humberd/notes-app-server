package pl.humberd.notesapp.application.query.workspace.model

import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

data class WorkspaceMinimalView(
    val id: WorkspaceId,
    val name: String
)
