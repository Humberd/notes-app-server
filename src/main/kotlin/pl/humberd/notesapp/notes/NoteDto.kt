package pl.humberd.notesapp.notes

import java.util.*

data class CreateNoteDto(
    val title: String,
    val content: String,
    val tags: Array<String>
)

data class NoteDto(
    val id: Long,
    val createdAt: Calendar,
    val lastModifiedAt: Calendar,
    val title: String,
    val content: String,
    val tags: List<NoteTagDto>
)

data class NoteTagDto(
    val id: String,
    val displayName: String
)
