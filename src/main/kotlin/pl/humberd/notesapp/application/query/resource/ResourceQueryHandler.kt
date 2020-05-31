package pl.humberd.notesapp.application.query.resource

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.UserId
import pl.humberd.notesapp.domain.repository.ResourceRepository
import kotlin.contracts.ExperimentalContracts

@Service
@ExperimentalContracts
class ResourceQueryHandler (
    private val resourceRepository: ResourceRepository,
    private val resourceViewMapper: ResourceViewMapper
) {
    fun ASSERT_IS_AUTHOR(resourceId: ResourceId, userId: UserId) {
        val resource = resourceRepository.findByIdOrNull(resourceId)
        ASSERT_NOT_NULL(resource, resourceId)
        if (resource.authorId != userId) {
            throw ForbiddenException(ResourceEntity::class, resourceId)
        }
    }

}
