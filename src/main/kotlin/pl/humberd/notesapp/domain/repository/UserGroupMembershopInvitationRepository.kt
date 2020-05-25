package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationId
import pl.humberd.notesapp.domain.entity.UserId
import java.util.*

@Repository
interface UserGroupMembershopInvitationRepository :
    RefreshableJpaRepository<UserGroupMembershipInvitationEntity, UserGroupMembershipInvitationId> {

    fun findByGroupIdAndInvitedUserId(groupId: GroupId, invitedUserId: UserId): Optional<UserGroupMembershipInvitationEntity>
}
