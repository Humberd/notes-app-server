package pl.humberd.notesapp.application.command.user_group_membership_invitation.model

import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationId
import pl.humberd.notesapp.domain.entity.UserId

data class UserGroupMembershipInvitationAcceptCommand(
    val invitationId: UserGroupMembershipInvitationId,
    val userId: UserId
)
