package pl.humberd.notesapp.infrastructure.http.note.model

import pl.humberd.notesapp.application.common.model.NameModel
import javax.validation.constraints.NotNull

class NoteCreateRequest {
    @NotNull
    lateinit var url: String

    @NotNull
    lateinit var title: String

    @NotNull
    lateinit var content: String

    var tags: List<NameModel>? = null
}
