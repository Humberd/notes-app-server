package pl.humberd.notesapp.application.command.workspace.model

import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

data class WorkspaceDeleteCommand(
    val id: WorkspaceId
)
