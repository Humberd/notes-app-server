package pl.humberd.notesapp.application.command.group_post

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.group_post.model.GroupPostCreateCommand
import pl.humberd.notesapp.application.command.notification.NotificationCommandHandler
import pl.humberd.notesapp.application.common.transaction.TransactionHelper
import pl.humberd.notesapp.application.query.group_post.GroupPostQueryHandler
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.GroupPostEntity
import pl.humberd.notesapp.domain.repository.GroupPostRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class GroupPostCommandHandler(
    private val groupPostRepository: GroupPostRepository,
    private val groupPostQueryHandler: GroupPostQueryHandler,
    private val notificationCommandHandler: NotificationCommandHandler
)  {

    fun create(command: GroupPostCreateCommand): GroupPostEntity {
        groupPostQueryHandler.ASSERT_NOT_EXISTS(
            groupId = command.groupId,
            resourceId = command.resourceId
        )

        val groupPostEntity = groupPostRepository.save(
            GroupPostEntity(
                id = IdGenerator.random(GroupPostEntity::class),
                groupId = command.groupId,
                resourceId = command.resourceId
            )
        )
        TransactionHelper.afterCommit {
            this.notificationCommandHandler.notifyGroupPostCreated(groupPostEntity)
        }

        return groupPostEntity
    }
}
