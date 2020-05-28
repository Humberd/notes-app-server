package pl.humberd.notesapp.application.query.user_group_membership_tag_trigger

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipTagTriggerEntityPK
import pl.humberd.notesapp.domain.entity.UserId
import pl.humberd.notesapp.domain.repository.UserGroupMembershipTagTriggerRepository
import kotlin.contracts.ExperimentalContracts

@Service
@ExperimentalContracts
class UserGroupMembershipTagTriggerQueryHandler(
    private val repository: UserGroupMembershipTagTriggerRepository
) {
    fun ASSERT_NOT_EXISTS(userId: UserId, tagId: TagId, groupId: GroupId) {
        val triggerExists = repository.existsById(
            UserGroupMembershipTagTriggerEntityPK().also {
                it.groupId = groupId
                it.tagId = tagId
                it.userId = userId
            }
        )

        ASSERT_NOT_EXIST(triggerExists, "Trigger for group{${groupId}} with tag{${tagId}} already exists")
    }
}
