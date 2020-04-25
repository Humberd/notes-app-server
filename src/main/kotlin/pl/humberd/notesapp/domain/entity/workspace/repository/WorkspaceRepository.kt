package pl.humberd.notesapp.domain.entity.workspace.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.workspace.model.Workspace
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceWithNoteIdProjection

@Repository
interface WorkspaceRepository : RefreshableJpaRepository<Workspace, WorkspaceId> {
    fun existsByUserIdAndNameLc(userId: UserId, nameLc: String): Boolean

    fun findAllByUserId(userId: UserId, pageable: Pageable): Page<Workspace>

    @Query("select workspace from Workspace workspace inner join NoteWorkspace nw on workspace.id = nw.id.workspaceId where nw.id.noteId = :noteId")
    fun findAllByNote(
        @Param("noteId") noteId: String,
        pageable: Pageable
    ): Page<Workspace>

    @Query("select workspace as workspaceInstance, nw.id.noteId as noteId from Workspace workspace inner join NoteWorkspace nw on workspace.id = nw.id.workspaceId where nw.id.noteId in (:noteIds)")
    fun PROJECT_findAllByNotes(
        @Param("noteIds") noteIds: Collection<String>
    ): List<WorkspaceWithNoteIdProjection>
}
