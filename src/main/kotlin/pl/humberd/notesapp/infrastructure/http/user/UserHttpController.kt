package pl.humberd.notesapp.infrastructure.http.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.application.query.user_group_membership_invitation.UserGroupMembershipInvitationQueryHandler
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewList
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewListFilter
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/users")
@RestController
class UserHttpController(
    private val userQueryHandler: UserQueryHandler,
    private val userGroupMembershipInvitationQueryHandler: UserGroupMembershipInvitationQueryHandler
) {

    @GetMapping("/{userId}")
    fun readProfile(
        @PathVariable("userId") userId: String
    ): ResponseEntity<UserView> {
        val view = userQueryHandler.view(userId)

        return ResponseBuilder.ok(view)
    }

    @GetMapping("/{userId}/group-invitations")
    fun readGroupInvitations(
        @PathVariable("userId") userId: String
    ): ResponseEntity<UserGroupMembershipInvitationViewList> {
        val viewList = userGroupMembershipInvitationQueryHandler.viewList(
            UserGroupMembershipInvitationViewListFilter.ByUser(userId)
        )

        return ResponseBuilder.ok(viewList)
    }

}
