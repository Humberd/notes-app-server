package pl.humberd.notesapp.application.command.resource_tag

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.group_post.GroupPostCommandHandler
import pl.humberd.notesapp.application.command.group_post.model.GroupPostCreateCommand
import pl.humberd.notesapp.application.command.resource_tag.model.ResourceTagCreateCommand
import pl.humberd.notesapp.application.query.resource_tag.ResourceTagQueryHandler
import pl.humberd.notesapp.domain.entity.ResourceTagEntity
import pl.humberd.notesapp.domain.repository.ResourceTagRepository
import pl.humberd.notesapp.domain.repository.UserGroupMembershipTagTriggerRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class ResourceTagCommandHandler(
    private val resourceTagRepository: ResourceTagRepository,
    private val tagTriggerRepository: UserGroupMembershipTagTriggerRepository,
    private val resourceTagQueryHandler: ResourceTagQueryHandler,
    private val groupPostCommandHandler: GroupPostCommandHandler
) {

    fun create(command: ResourceTagCreateCommand): ResourceTagEntity {
        resourceTagQueryHandler.ASSERT_NOT_EXISTS(
            tagId = command.tagId,
            resourceId = command.resourceId
        )

        val resourceTagEntity = resourceTagRepository.save(
            ResourceTagEntity(
                resourceId = command.resourceId,
                tagId = command.tagId
            )
        )

        val tagTriggers = tagTriggerRepository.findAllByTagId(command.tagId)
        tagTriggers.filter { !command.omitPublishingToGroupIds.contains(it.groupId) }
            .forEach { tagTrigger ->
                groupPostCommandHandler.create(
                    GroupPostCreateCommand(
                        groupId = tagTrigger.groupId,
                        resourceId = command.resourceId
                    )
                )
            }

        return resourceTagEntity
    }
}