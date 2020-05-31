package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupPostId
import pl.humberd.notesapp.domain.entity.GroupPostUserStateEntity
import pl.humberd.notesapp.domain.entity.GroupPostUserStateEntityPK
import pl.humberd.notesapp.domain.entity.UserId

@Repository
interface GroupPostUserStateRepository :
    RefreshableJpaRepository<GroupPostUserStateEntity, GroupPostUserStateEntityPK> {

    fun findByGroupPostIdAndUserId(groupPostId: GroupPostId, userId: UserId): GroupPostUserStateEntity?
}
