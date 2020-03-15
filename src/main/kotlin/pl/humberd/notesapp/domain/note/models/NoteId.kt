package pl.humberd.notesapp.domain.note.models

import pl.humberd.notesapp.domain.user.models.UserId
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class NoteId(
    @Column(name = "id")
    val id: String,

    @Column(name = "author_id")
    val authorId: UserId
): Serializable
