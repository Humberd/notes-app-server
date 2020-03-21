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


/*
class NoteCreateRequest(
    @field:NotNull
    var url: String,

    @field:NotNull
    var title: String,

    @field:NotNull
    var content: String
)
*/
