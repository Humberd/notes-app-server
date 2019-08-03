package pl.humberd.notesapp.notes

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import pl.humberd.notesapp.tags.Tag
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Note(
    @Id
    @GeneratedValue
    val id: Long,

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Calendar?,

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var lastModifiedAt: Calendar?,

    var title: String,
    var content: String,

    @ManyToMany(cascade = [])
    @JoinTable(
        name = "note_tag",
        joinColumns = [JoinColumn(name = "note_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    var tags: Set<Tag> = hashSetOf()
) {
    override fun toString(): String {
        return "Note(id=$id, createdAt=${createdAt?.toInstant().toString()}, lastModifiedAt=${lastModifiedAt?.toInstant().toString()}, title='$title', content='$content')"
    }


}
