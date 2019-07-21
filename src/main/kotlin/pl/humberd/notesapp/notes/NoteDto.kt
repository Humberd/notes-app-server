package pl.humberd.notesapp.notes

data class CreateNoteDto(
    val title: String,
    val content: String,
    val tags: Array<String>
)

data class NoteDto(
    val id: Long,
    val title: String,
    val content: String,
    val tags: List<NoteTagDto>
)

data class NoteTagDto(
    val id: String,
    val displayName: String
)
