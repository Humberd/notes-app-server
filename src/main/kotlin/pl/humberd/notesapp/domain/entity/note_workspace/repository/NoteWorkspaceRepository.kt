package pl.humberd.notesapp.domain.entity.note_workspace.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note_workspace.model.NoteWorkspaceId

@Repository
interface NoteWorkspaceRepository : RefreshableJpaRepository<NoteWorkspaceRepository, NoteWorkspaceId>
