package pl.humberd.notesapp.infrastructure.http.group.model

import pl.humberd.notesapp.application.common.model.IdModel
import javax.validation.constraints.NotBlank

class GroupCreateRequest {
    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var iconUrl: String

    var invitedUsers: Array<IdModel> = emptyArray()
}
