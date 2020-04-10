package pl.humberd.notesapp.infrastructure.http.note.model

import java.util.*

class NotePatchRequest {
    var title: String? = null
    var url: Optional<String>? = null
    var content: Optional<String>? = null
}
