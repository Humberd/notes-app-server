package pl.humberd.notesapp.application.command.user_group_membership_invitation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationCreateCommand
import pl.humberd.notesapp.application.common.transaction.TransactionsHelper
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository
import pl.humberd.notesapp.domain.repository.UserGroupMembershopInvitationRepository


@Service
@Transactional
class UserGroupMembershipInvitationCommandHandler(
    private val invitationRepository: UserGroupMembershopInvitationRepository,
    private val userGroupMembershipRepository: UserGroupMembershipRepository
) {

    fun create(command: UserGroupMembershipInvitationCreateCommand): UserGroupMembershipInvitationEntity {
        val existingInvitation = invitationRepository.findByGroupIdAndInvitedUserId(
            groupId = command.groupId,
            invitedUserId = command.invitedUserId
        )
        if (existingInvitation.isPresent) {
            throw AlreadyExistsException(
                UserGroupMembershipInvitationEntity::class,
                "groupId=${command.groupId}, invitedUserId=${command.invitedUserId}"
            )
        }

        val existingMembership = userGroupMembershipRepository.findById(
            UserGroupMembershipEntityPK().also {
                it.groupId = command.groupId
                it.userId = command.invitedUserId
            }
        )
        if (existingMembership.isPresent) {
            throw AlreadyExistsException("${command.invitedUserId} is already a member of a group: ${command.groupId}")
        }

        val invitation = invitationRepository.save(
            UserGroupMembershipInvitationEntity(
                id = IdGenerator.random(UserGroupMembershipInvitationEntity::class),
                groupId = command.groupId,
                invitedByUserId = command.invitedByUserId,
                invitedUserId = command.invitedUserId
            )
        )

        TransactionsHelper.afterCommit {
            println("I have commited this invitation: $invitation")
        }

        return invitation
    }

}
