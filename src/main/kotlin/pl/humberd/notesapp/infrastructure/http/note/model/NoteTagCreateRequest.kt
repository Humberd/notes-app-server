package pl.humberd.notesapp.infrastructure.http.note.model

import javax.validation.constraints.NotBlank

class NoteTagCreateRequest {
    @NotBlank
    lateinit var tagName: String
}
