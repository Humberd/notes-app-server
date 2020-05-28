package pl.humberd.notesapp.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.GroupPostEntity
import pl.humberd.notesapp.domain.entity.GroupPostId

interface GroupPostRepository : RefreshableJpaRepository<GroupPostEntity, GroupPostId> {
    fun findAllByGroupId(groupId: GroupId, pageable: Pageable): Page<GroupPostEntity>
}
