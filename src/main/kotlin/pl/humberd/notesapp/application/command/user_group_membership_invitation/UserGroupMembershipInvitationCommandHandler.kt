package pl.humberd.notesapp.application.command.user_group_membership_invitation

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.notification.NotificationCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership.UserGroupMembershipCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership.model.UserGroupMembershipCreateCommand
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationAcceptCommand
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationCreateCommand
import pl.humberd.notesapp.application.common.asserts.ASSERT_EXIST
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.common.transaction.TransactionHelper
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository
import pl.humberd.notesapp.domain.repository.UserGroupMembershopInvitationRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class UserGroupMembershipInvitationCommandHandler(
    private val invitationRepository: UserGroupMembershopInvitationRepository,
    private val userGroupMembershipRepository: UserGroupMembershipRepository,
    private val notificationCommandHandler: NotificationCommandHandler,
    private val groupMembershipCommandHandler: UserGroupMembershipCommandHandler
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

        TransactionHelper.afterCommit {
            notificationCommandHandler.notify(invitation)
        }

        return invitation
    }

    fun accept(command: UserGroupMembershipInvitationAcceptCommand) {
        val invitation = invitationRepository.findByIdOrNull(command.invitationId)
        ASSERT_NOT_NULL(invitation, command.invitationId)

        val isInvitedUser = invitation.invitedUserId == command.userId
        ASSERT_EXIST(isInvitedUser, "invitation does not belong to the user")

        groupMembershipCommandHandler.create(
            UserGroupMembershipCreateCommand(
                groupId = invitation.groupId,
                userId = invitation.invitedUserId
            )
        )
        invitationRepository.delete(invitation)
    }


}
