package pl.humberd.notesapp.application.command.workspace.model

import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

data class WorkspacePatchCommand(
    val id: WorkspaceId,
    val userId: UserId,
    val name: String?
)
