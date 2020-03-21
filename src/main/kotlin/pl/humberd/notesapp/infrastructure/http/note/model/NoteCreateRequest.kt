package pl.humberd.notesapp.infrastructure.http.note.model

import javax.validation.constraints.NotNull

class NoteCreateRequest(
    @field:NotNull
    var url: String,

    @field:NotNull
    var title: String,

    @field:NotNull
    var content: String
)
