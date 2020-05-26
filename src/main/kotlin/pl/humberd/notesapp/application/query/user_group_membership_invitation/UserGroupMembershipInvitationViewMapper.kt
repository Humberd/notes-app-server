package pl.humberd.notesapp.application.query.user_group_membership_invitation

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.GroupViewMapper
import pl.humberd.notesapp.application.query.user.UserViewMapper
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationView
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity

@Service
class UserGroupMembershipInvitationViewMapper(
    private val groupViewMapper: GroupViewMapper,
    private val userViewMapper: UserViewMapper
) {
    fun mapView(invitation: UserGroupMembershipInvitationEntity): UserGroupMembershipInvitationView =
        UserGroupMembershipInvitationView(
            id = invitation.id,
            group = groupViewMapper.mapMinimalView(invitation.groupId),
            invitedByUser = userViewMapper.mapMinimalView(invitation.invitedByUserId),
            invitedUser = userViewMapper.mapMinimalView(invitation.invitedUserId)
        )
}
