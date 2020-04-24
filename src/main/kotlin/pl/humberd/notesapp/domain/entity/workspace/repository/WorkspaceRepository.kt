package pl.humberd.notesapp.domain.entity.workspace.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.workspace.model.Workspace
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

@Repository
interface WorkspaceRepository : RefreshableJpaRepository<Workspace, WorkspaceId>
