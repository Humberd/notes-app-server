package pl.humberd.notesapp.infrastructure.http.note.model

import javax.validation.constraints.NotNull

class NoteCreateRequest {
    @NotNull
    lateinit var url: String

    @NotNull
    lateinit var title: String

    @NotNull
    lateinit var content: String
}
