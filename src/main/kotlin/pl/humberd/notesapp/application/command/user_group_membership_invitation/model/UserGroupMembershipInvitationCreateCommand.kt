package pl.humberd.notesapp.application.command.user_group_membership_invitation.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserId

data class UserGroupMembershipInvitationCreateCommand(
    val groupId: GroupId,
    val invitedByUserId: UserId,
    val invitedUserId: UserId
)
