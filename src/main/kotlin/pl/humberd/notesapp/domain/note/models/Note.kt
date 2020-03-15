package pl.humberd.notesapp.domain.note.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.user.models.User
import javax.persistence.*

@Entity
@Table(name = "NOTE")
class Note(
    @EmbeddedId
    val id: NoteId,

    @Column(name = "url")
    val url: String,

    @Column(name = "title")
    val title: String,

    @Column(name = "content")
    val content: String
) {
    @Column(name = "is_starred")
    var isStarred: Boolean = false

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false

    @MapsId("author_id")
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    lateinit var user: User

    @Embedded
    lateinit var metadata: EntityMetadata

    override fun toString(): String {
        return "Note(id=$id, url='$url', title='$title', content='$content', isStarred=$isStarred, isDeleted=$isDeleted, user=$user, metadata=$metadata)"
    }
}
