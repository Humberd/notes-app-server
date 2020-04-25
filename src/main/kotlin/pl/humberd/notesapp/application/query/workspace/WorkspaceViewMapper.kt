package pl.humberd.notesapp.application.query.workspace

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceMinimalView
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceView
import pl.humberd.notesapp.domain.entity.workspace.model.Workspace

@Service
class WorkspaceViewMapper {
    fun mapView(workspace: Workspace) = WorkspaceView(
        id = workspace.id,
        name = workspace.name,
        createdAt = workspace.metadata.createdAt,
        updatedAt = workspace.metadata.updatedAt
    )

    fun mapMinimalView(workspace: Workspace) = WorkspaceMinimalView(
        id = workspace.id,
        name = workspace.name
    )
}
