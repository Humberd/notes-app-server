package pl.humberd.notesapp.infrastructure.http.resource.model

import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import javax.validation.constraints.NotNull

class ResourceUpdateRequest {
    @NotNull
    lateinit var payload: ResourceRevisionEntity.Payload.Link
}