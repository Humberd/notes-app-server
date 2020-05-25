package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitation
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationId
import pl.humberd.notesapp.domain.entity.UserId
import java.util.*

@Repository
interface UserGroupMembershopInvitationRepository :
    RefreshableJpaRepository<UserGroupMembershipInvitation, UserGroupMembershipInvitationId> {

    fun findByGroupIdAndInvitedUserId(groupId: GroupId, invitedUserId: UserId): Optional<UserGroupMembershipInvitation>
}
