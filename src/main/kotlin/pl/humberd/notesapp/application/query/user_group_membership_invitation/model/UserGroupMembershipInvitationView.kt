package pl.humberd.notesapp.application.query.user_group_membership_invitation.model

import pl.humberd.notesapp.application.query.group.model.GroupMinimalView
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationId

data class UserGroupMembershipInvitationView(
    val id: UserGroupMembershipInvitationId,
    val group: GroupMinimalView,
    val invitedByUser: UserMinimalView,
    val invitedUser: UserMinimalView
)
