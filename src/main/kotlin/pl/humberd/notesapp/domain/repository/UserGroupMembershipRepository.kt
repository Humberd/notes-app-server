package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK

@Repository
interface UserGroupMembershipRepository :
    RefreshableJpaRepository<UserGroupMembershipEntity, UserGroupMembershipEntityPK> {

    fun findAllByGroupId(groupId: GroupId): List<UserGroupMembershipEntity>
}
