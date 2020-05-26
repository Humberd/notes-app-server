package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK
import pl.humberd.notesapp.domain.entity.UserId

@Repository
interface UserGroupMembershipRepository :
    RefreshableJpaRepository<UserGroupMembershipEntity, UserGroupMembershipEntityPK> {

    fun existsByUserIdAndGroupId(userId: UserId, groupId: GroupId): Boolean
}
