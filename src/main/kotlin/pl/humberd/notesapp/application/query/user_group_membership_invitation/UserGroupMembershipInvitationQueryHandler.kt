package pl.humberd.notesapp.application.query.user_group_membership_invitation

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.list_view.ListViewExtra
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewList
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewListFilter
import pl.humberd.notesapp.domain.repository.UserGroupMembershopInvitationRepository

@Service
class UserGroupMembershipInvitationQueryHandler(
    private val userGroupMembershopInvitationRepository: UserGroupMembershopInvitationRepository,
    private val userGroupMembershipInvitationViewMapper: UserGroupMembershipInvitationViewMapper
) {
    fun viewList(
        filter: UserGroupMembershipInvitationViewListFilter
    ): UserGroupMembershipInvitationViewList {
        val page = when (filter) {
            is UserGroupMembershipInvitationViewListFilter.ByUser ->
                userGroupMembershopInvitationRepository.findAllByInvitedUserId(
                    invitedUserId = filter.userId,
                    pageable = Pageable.unpaged()
                )
            is UserGroupMembershipInvitationViewListFilter.ByGroup ->
                userGroupMembershopInvitationRepository.findAllByGroupId(
                    groupId = filter.groupId,
                    pageable = Pageable.unpaged()
                )
        }

        return UserGroupMembershipInvitationViewList(
            data = page.content.map(userGroupMembershipInvitationViewMapper::mapView),
            extra = ListViewExtra.from(page)
        )
    }
}
