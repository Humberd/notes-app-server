package pl.humberd.notesapp.infrastructure.http.note.model

import pl.humberd.notesapp.application.common.model.IdModel
import pl.humberd.notesapp.application.common.model.NameModel
import java.util.*

class NotePatchRequest {
    var title: String? = null
    var url: Optional<String>? = null
    var content: Optional<String>? = null
    var tags: Collection<NameModel>? = null
    var workspaces: Collection<IdModel>? = null
}
