package pl.humberd.notesapp.application.command.user_group_membership

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.user_group_membership.model.UserGroupMembershipCreateCommand
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class UserGroupMembershipCommandHandler(
    private val userGroupMembershipRepository: UserGroupMembershipRepository
) {

    fun create(command: UserGroupMembershipCreateCommand): UserGroupMembershipEntity {
        val alreadyAMember = userGroupMembershipRepository.existsById(
            UserGroupMembershipEntityPK().also {
                it.groupId = command.groupId
                it.userId = command.userId
            }
        )
        ASSERT_NOT_EXIST(alreadyAMember, "User ${command.userId} is already a member of group ${command.groupId}")

        val membershipEntity = userGroupMembershipRepository.save(
            UserGroupMembershipEntity(
                userId = command.userId,
                groupId = command.groupId
            )
        )

        return membershipEntity
    }
}
