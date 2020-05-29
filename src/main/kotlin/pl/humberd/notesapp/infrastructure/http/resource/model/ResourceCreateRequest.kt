package pl.humberd.notesapp.infrastructure.http.resource.model

import pl.humberd.notesapp.application.common.model.IdModel
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import javax.validation.constraints.NotNull

class ResourceCreateRequest {
    @NotNull
    lateinit var payload: ResourceRevisionEntity.Payload.Link

    @NotNull
    lateinit var publishSettings: PublishSettings

    class PublishSettings {
        @NotNull
        lateinit var tags: Array<IdModel>

        @NotNull
        lateinit var omittedGroups: Array<IdModel>
    }
}
