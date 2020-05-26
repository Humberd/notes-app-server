package pl.humberd.notesapp.application.query.user_group_membership

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_EXIST
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntityPK
import pl.humberd.notesapp.domain.entity.UserId
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository
import kotlin.contracts.ExperimentalContracts

@Service
@ExperimentalContracts
class UserGroupMembershipQueryHandler(
    private val userGroupMembershipRepository: UserGroupMembershipRepository
) {
    fun ASSERT_GROUP_MEMBERSHIP(userId: UserId, groupId: GroupId) {
        val userExists = userGroupMembershipRepository.existsById(
            UserGroupMembershipEntityPK().also {
                it.userId = userId
                it.groupId = groupId
            }
        )
        ASSERT_EXIST(userExists, "${userId} is not a member of a group ${groupId}")
    }
}
