package pl.humberd.notesapp.domain.note.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", referencedColumnName = "id")
    lateinit var refNote: Note

    override fun toString(): String {
        return "NoteComment(id='$id', authorId=$authorId, noteId='$noteId', content='$content', metadata=$metadata)"
    }

}
