package pl.humberd.notesapp.application.command.user_group_membership_tag_trigger

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.user_group_membership_tag_trigger.model.UserGroupMembershipTagTriggerCreateCommand
import pl.humberd.notesapp.application.query.tag.TagQueryHandler
import pl.humberd.notesapp.application.query.user_group_membership.UserGroupMembershipQueryHandler
import pl.humberd.notesapp.application.query.user_group_membership_tag_trigger.UserGroupMembershipTagTriggerQueryHandler
import pl.humberd.notesapp.domain.entity.UserGroupMembershipTagTriggerEntity
import pl.humberd.notesapp.domain.repository.UserGroupMembershipTagTriggerRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class UserGroupMembershipTagTriggerCommandHandler(
    private val triggerRepository: UserGroupMembershipTagTriggerRepository,
    private val userGroupMembershipQueryHandler: UserGroupMembershipQueryHandler,
    private val tagQueryHandler: TagQueryHandler,
    private val triggerQueryHandler: UserGroupMembershipTagTriggerQueryHandler
)  {

    fun create(command: UserGroupMembershipTagTriggerCreateCommand): UserGroupMembershipTagTriggerEntity {
        userGroupMembershipQueryHandler.ASSERT_GROUP_MEMBERSHIP(
            userId = command.userId,
            groupId = command.groupId
        )

        tagQueryHandler.ASSERT_EXISTS(command.tagId)

        triggerQueryHandler.ASSERT_NOT_EXISTS(
            userId = command.userId,
            groupId = command.groupId,
            tagId = command.groupId
        )

        val entity = triggerRepository.save(
            UserGroupMembershipTagTriggerEntity(
                userId = command.userId,
                groupId = command.groupId,
                tagId = command.tagId
            )
        )

        return entity
    }
}
