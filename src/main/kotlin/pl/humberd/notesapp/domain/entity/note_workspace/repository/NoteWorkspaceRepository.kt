package pl.humberd.notesapp.domain.entity.note_workspace.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note_workspace.model.NoteWorkspace
import pl.humberd.notesapp.domain.entity.note_workspace.model.NoteWorkspaceId
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

@Repository
interface NoteWorkspaceRepository : RefreshableJpaRepository<NoteWorkspace, NoteWorkspaceId> {
    fun findAllByIdWorkspaceId(workspaceId: WorkspaceId): List<NoteWorkspace>
}
