package pl.humberd.notesapp.domain.note.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.user.models.User
import pl.humberd.notesapp.domain.user.models.UserId
import javax.persistence.*

@Entity
@Table(name = "note")
class Note(
    @Id
    @Column(name = "id")
    val id: NoteId,

    @Column(name = "author_id")
    val authorId: UserId,

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

    @Embedded
    lateinit var metadata: EntityMetadata

    @MapsId("author_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    lateinit var refUser: User

    override fun toString(): String {
        return "Note(id=$id, url='$url', title='$title', content='$content', isStarred=$isStarred, isDeleted=$isDeleted, user=$refUser, metadata=$metadata)"
    }
}
