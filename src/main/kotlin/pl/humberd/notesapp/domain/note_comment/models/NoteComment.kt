package pl.humberd.notesapp.domain.note_comment.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.note.models.Note
import pl.humberd.notesapp.domain.user.models.User
import javax.persistence.*

@Entity
@Table(name = "note_comment")
class NoteComment(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "author_id")
    var authorId: String?,

    @Column(name = "note_id")
    var noteId: String,

    @Column(name = "content")
    var content: String
) {

    @Embedded
    lateinit var metadata: EntityMetadata

    @MapsId("note_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    lateinit var refNote: Note

    @MapsId("author_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    lateinit var refAuthor: User

    override fun toString(): String {
        return "NoteComment(id='$id', authorId=$authorId, noteId='$noteId', content='$content')"
    }

}
