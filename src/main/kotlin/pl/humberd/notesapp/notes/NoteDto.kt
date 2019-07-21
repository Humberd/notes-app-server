package pl.humberd.notesapp.notes

data class CreateNoteDto(
    val title: String,
    val content: String,
    val tags: Array<String>
)
