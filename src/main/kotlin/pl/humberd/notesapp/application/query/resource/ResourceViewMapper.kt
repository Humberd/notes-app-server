package pl.humberd.notesapp.application.query.resource

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.resource.model.ResourceView
import pl.humberd.notesapp.application.query.resource_revision.ResourceRevisionViewMapper
import pl.humberd.notesapp.application.query.user.UserViewMapper
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.UserId
import pl.humberd.notesapp.domain.repository.ResourceRepository

@Service
class ResourceViewMapper(
    private val resourceRepository: ResourceRepository,
    private val userViewMapper: UserViewMapper,
    private val revisionViewMapper: ResourceRevisionViewMapper
) {
    fun mapView(entity: ResourceEntity, userId: UserId) = ResourceView(
        id = entity.id,
        author = userViewMapper.mapMinimalView(entity.authorId),
        userSpecificRevision = revisionViewMapper.mapView(entity.latestRevisionId),
        revisionsCount = entity.revisionsCount,
        latestRevisionId = entity.latestRevisionId,
        createdAt = entity.createdAt
    )

    fun mapView(id: ResourceId, userId: UserId): ResourceView {
        return mapView(resourceRepository.findById(id).get(), userId)
    }
}
