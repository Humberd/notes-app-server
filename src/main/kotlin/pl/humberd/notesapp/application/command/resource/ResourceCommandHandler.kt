package pl.humberd.notesapp.application.command.resource

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.resource.model.ResourceCreateCommand
import pl.humberd.notesapp.application.command.resource_revision.ResourceRevisionCommandHandler
import pl.humberd.notesapp.application.command.resource_revision.model.ResourceRevisionCreateCommand
import pl.humberd.notesapp.application.command.resource_tag.ResourceTagCommandHandler
import pl.humberd.notesapp.application.command.resource_tag.model.ResourceTagCreateCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.repository.ResourceRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class ResourceCommandHandler(
    private val resourceRepository: ResourceRepository,
    private val resourceRevisionCommandHandler: ResourceRevisionCommandHandler,
    private val resourceTagCommandHandler: ResourceTagCommandHandler
) {

    fun create(command: ResourceCreateCommand): ResourceEntity {
        val resource = resourceRepository.save(
            ResourceEntity(
                id = IdGenerator.random(ResourceEntity::class),
                authorId = command.authorId
            )
        )

        val resourceRevision = resourceRevisionCommandHandler.create(
            ResourceRevisionCreateCommand(
                resourceId = resource.id,
                changeKind = ResourceRevisionEntity.ResourceChangeKind.INSERT,
                payload = command.payload
            )
        )
        resource.latestRevisionId = resourceRevision.id

        command.tagIds.forEach { tagId ->
            resourceTagCommandHandler.create(
                ResourceTagCreateCommand(
                    tagId = tagId,
                    resourceId = resource.id,
                    omitPublishingToGroupIds = command.omittedGroups
                )
            )
        }

        return resource
    }
}
