package pl.humberd.notesapp.domain.entity.workspace.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.workspace.model.Workspace
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

@Repository
interface WorkspaceRepository : RefreshableJpaRepository<Workspace, WorkspaceId> {
    fun existsByUserIdAndNameLc(userId: UserId, nameLc: String): Boolean

    fun findAllByUserId(userId: UserId, pageable: Pageable): Page<Workspace>
}
