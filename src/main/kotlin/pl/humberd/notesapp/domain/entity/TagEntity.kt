package pl.humberd.notesapp.domain.entity

import pl.humberd.notesapp.domain.common.EntityMetadata
import javax.persistence.*

typealias TagId = String

@Entity
@Table(name = "tag", schema = "public")
class TagEntity(
    @Id
    @Column(name = "id")
    val id: TagId,

    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "name")
    var name: String,

    @Column(name = "background_color")
    var backgroundColor: String?
) {

    @Column(name = "name_lc", updatable = false, insertable = false)
    var nameLc: String = ""
        private set

    @Column(name = "notes_count", updatable = false, insertable = false)
    var notesCount: Int = 0
        private set

    @Embedded
    var metadata = EntityMetadata()
        private set

    override fun toString(): String {
        return "Tag(id='$id', userId='$userId', name='$name', backgroundColor='$backgroundColor', nameLc='$nameLc', notesCount=$notesCount)"
    }

}
