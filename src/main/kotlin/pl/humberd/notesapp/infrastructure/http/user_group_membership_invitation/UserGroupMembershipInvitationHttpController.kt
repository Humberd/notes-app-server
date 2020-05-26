package pl.humberd.notesapp.infrastructure.http.user_group_membership_invitation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.user_group_membership_invitation.UserGroupMembershipInvitationCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationAcceptCommand
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationId
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import java.security.Principal
import kotlin.contracts.ExperimentalContracts

@RequestMapping("/group-membership-invitations")
@RestController
@ExperimentalContracts
class UserGroupMembershipInvitationHttpController (
    private val userGroupMembershipInvitationCommandHandler: UserGroupMembershipInvitationCommandHandler
) {

    @PostMapping("/{invitationId}/accept")
    fun accept(
        @PathVariable("invitationId") invitationId: UserGroupMembershipInvitationId,
        principal: Principal
    ): ResponseEntity<Unit> {
        userGroupMembershipInvitationCommandHandler.accept(
            UserGroupMembershipInvitationAcceptCommand(
                invitationId = invitationId,
                userId = principal.name
            )
        )

        return ResponseBuilder.noContent()
    }
}
