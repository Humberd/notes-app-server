package pl.humberd.notesapp.application.command.user_group_membership_invitation

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationCreateCommand
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitation
import pl.humberd.notesapp.domain.repository.UserGroupMembershopInvitationRepository
import javax.transaction.Transactional

@Service
@Transactional
class UserGroupMembershipInvitationCommandHandler(
    private val invitationRepository: UserGroupMembershopInvitationRepository
) {

    fun create(command: UserGroupMembershipInvitationCreateCommand): UserGroupMembershipInvitation {
        val existingInvitation = invitationRepository.findByGroupIdAndInvitedUserId(
            groupId = command.groupId,
            invitedUserId = command.invitedUserId
        )
        if (existingInvitation.isPresent) {
            throw AlreadyExistsException(UserGroupMembershipInvitation::class, "groupId=${command.groupId}, invitedUserId=${command.invitedUserId}")
        }

        val invitation = invitationRepository.save(
            UserGroupMembershipInvitation(
                id = IdGenerator.random(UserGroupMembershipInvitation::class),
                groupId = command.groupId,
                invitedByUserId = command.invitedByUserId,
                invitedUserId = command.invitedUserId
            )
        )

        return invitation
    }

}
