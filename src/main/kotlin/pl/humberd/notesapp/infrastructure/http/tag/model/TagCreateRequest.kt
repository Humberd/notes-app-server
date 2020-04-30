package pl.humberd.notesapp.infrastructure.http.tag.model

import javax.validation.constraints.NotNull

class TagCreateRequest {
    @NotNull
    lateinit var name: String
    var backgroundColor: String? = null
}
