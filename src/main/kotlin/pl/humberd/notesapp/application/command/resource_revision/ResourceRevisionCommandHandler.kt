package pl.humberd.notesapp.application.command.resource_revision

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.resource_revision.model.ResourceRevisionCreateCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.repository.ResourceRevisionRepository

@Service
@Transactional
class ResourceRevisionCommandHandler(
    private val resourceRevisionRepository: ResourceRevisionRepository
) {

    fun create(command: ResourceRevisionCreateCommand): ResourceRevisionEntity {
        val type = when (command.payload) {
            is ResourceRevisionEntity.Payload.Link -> ResourceRevisionEntity.ResourceType.LINK
            else -> throw NotImplementedError()
        }

        val resourceRevisionEntity = resourceRevisionRepository.save(
            ResourceRevisionEntity(
                id = IdGenerator.random(ResourceRevisionEntity::class),
                resourceId = command.resourceId,
                changeKind = command.changeKind,
                type = type,
                payload = command.payload
            )
        )

        return resourceRevisionEntity
    }
}
