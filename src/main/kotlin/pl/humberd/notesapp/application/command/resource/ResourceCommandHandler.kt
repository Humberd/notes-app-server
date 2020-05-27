package pl.humberd.notesapp.application.command.resource

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.resource.model.ResourceCreateCommand
import pl.humberd.notesapp.application.command.resource_revision.ResourceRevisionCommandHandler
import pl.humberd.notesapp.application.command.resource_revision.model.ResourceRevisionCreateCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.repository.ResourceRepository

@Service
@Transactional
class ResourceCommandHandler(
    private val resourceRepository: ResourceRepository,
    private val resourceRevisionCommandHandler: ResourceRevisionCommandHandler
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

        return resource
    }
}
