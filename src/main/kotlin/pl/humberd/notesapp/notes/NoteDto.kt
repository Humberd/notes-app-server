package pl.humberd.notesapp.notes

import pl.humberd.notesapp.tags.TagDto

data class CreateNoteDto(
    val title: String,
    val content: String,
    val tags: Array<String>
)

data class NoteDto(
    val id: Long,
    val title: String,
    val content: String,
    val tags: List<TagDto>
)
