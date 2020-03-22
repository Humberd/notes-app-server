package pl.humberd.notesapp.domain.entity.tag.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.user.model.UserId
import javax.persistence.*

typealias TagId = String

@Entity
@Table(name = "tag", schema = "public")
class Tag(
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
    lateinit var nameLc: String
        private set

    @Column(name = "notes_count", updatable = false, insertable = false)
    var notesCount: Int = 0
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "Tag(id='$id', userId='$userId', name='$name', backgroundColor='$backgroundColor', nameLc='$nameLc', notesCount=$notesCount)"
    }

}
