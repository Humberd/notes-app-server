package pl.humberd.notesapp.application.query.user_group_membership_invitation.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserId

sealed class UserGroupMembershipInvitationViewListFilter {
    class ByGroup(
        val groupId: GroupId
    ): UserGroupMembershipInvitationViewListFilter()

    class ByUser(
        val userId: UserId
    ): UserGroupMembershipInvitationViewListFilter()

}
