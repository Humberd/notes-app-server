package pl.humberd.notesapp.application.query.resource_revision

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.resource_revision.model.ResourceRevisionView
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionId
import pl.humberd.notesapp.domain.repository.ResourceRevisionRepository

@Service
class ResourceRevisionViewMapper(
    private val resourceRevisionRepository: ResourceRevisionRepository
) {
    fun mapView(entity: ResourceRevisionEntity) = ResourceRevisionView(
        id = entity.id,
        resourceId = entity.resourceId,
        changeKind = entity.changeKind,
        type = entity.type,
        payload = entity.payload,
        createdAt = entity.createdAt
    )

    fun mapView(id: ResourceRevisionId): ResourceRevisionView {
        return mapView(resourceRevisionRepository.findById(id).get())
    }
}
