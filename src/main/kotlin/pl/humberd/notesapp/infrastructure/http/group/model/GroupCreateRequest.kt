package pl.humberd.notesapp.infrastructure.http.group.model

import javax.validation.constraints.NotBlank

class GroupCreateRequest {
    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var iconUrl: String
}
