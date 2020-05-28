package pl.humberd.notesapp.infrastructure.http.tag.model

import pl.humberd.notesapp.application.common.model.IdModel
import javax.validation.constraints.NotNull

class TagCreateRequest {
    @NotNull
    lateinit var name: String
    var backgroundColor: String? = null
    @NotNull
    lateinit var publishSettings: PublishSettings

    class PublishSettings {
        @NotNull
        lateinit var groups: Array<IdModel>
    }
}
