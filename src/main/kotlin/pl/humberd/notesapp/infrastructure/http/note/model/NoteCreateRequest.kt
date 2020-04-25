package pl.humberd.notesapp.infrastructure.http.note.model

import pl.humberd.notesapp.application.common.model.IdModel
import pl.humberd.notesapp.application.common.model.NameModel
import javax.validation.constraints.NotNull

class NoteCreateRequest {
    var url: String? = null
    @NotNull
    lateinit var title: String
    var content: String? = null
    var tags: List<NameModel>? = null
    var workspaces: List<IdModel>? = null
}
