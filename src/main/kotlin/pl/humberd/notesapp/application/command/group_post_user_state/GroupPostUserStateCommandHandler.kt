package pl.humberd.notesapp.application.command.group_post_user_state

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.group_post_user_state.model.GroupPostUserStateCreateCommand
import pl.humberd.notesapp.application.command.group_post_user_state.model.GroupPostUserStateUpdateCommand
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.domain.entity.GroupPostUserStateEntity
import pl.humberd.notesapp.domain.repository.GroupPostUserStateRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class GroupPostUserStateCommandHandler(
    private val groupPostUserStateRepository: GroupPostUserStateRepository
)  {
    fun create(command: GroupPostUserStateCreateCommand): GroupPostUserStateEntity {
        val userStateEntity = groupPostUserStateRepository.save(
            GroupPostUserStateEntity(
                groupPostId = command.groupPostId,
                userId = command.userId,
                resourceRevisionId = command.resourceRevisionId
            )
        )

        return userStateEntity
    }

    fun update(command: GroupPostUserStateUpdateCommand): GroupPostUserStateEntity {
        val groupPostUserStateEntity = groupPostUserStateRepository.findByGroupPostIdAndUserId(
            groupPostId = command.groupPostId,
            userId = command.userId
        )
        ASSERT_NOT_NULL(groupPostUserStateEntity, "groupPostId: ${command.groupPostId}, userId: ${command.userId}")

        groupPostUserStateEntity.resourceRevisionId = command.newResourceRevisionId

        return groupPostUserStateEntity
    }
}