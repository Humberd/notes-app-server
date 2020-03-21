package pl.humberd.notesapp.infrastructure.http.note.model

data class NotePatchRequest(
    val url: String?,
    val title: String?,
    val content: String?
)
