package pl.humberd.notesapp.infrastructure.http.user_group_membership_invitation

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.user_group_membership_invitation.UserGroupMembershipInvitationCommandHandler

@RequestMapping("/group-membership/invitations")
@RestController
class UserGroupMembershipInvitationHttpController (
    private val userGroupMembershipInvitationCommandHandler: UserGroupMembershipInvitationCommandHandler
)
