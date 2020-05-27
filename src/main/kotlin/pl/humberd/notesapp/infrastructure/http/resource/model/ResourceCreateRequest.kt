package pl.humberd.notesapp.infrastructure.http.resource.model

import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity

class ResourceCreateRequest {
    lateinit var payload: ResourceRevisionEntity.Payload.Link
}
