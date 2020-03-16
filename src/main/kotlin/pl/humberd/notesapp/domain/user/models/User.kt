package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.note.models.Note
import javax.persistence.*

@Entity
@Table(name = "user", schema = "public")
class User(
    @Id
    @Column(name = "id")
    val id: UserId,

    @Column(name = "name")
    var name: String
) {

    @Column(name = "name_lc", updatable = false, insertable = false)
    lateinit var nameLc: String
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    @OneToOne(mappedBy = "refUser", fetch = FetchType.LAZY)
    lateinit var refUserAuth: UserAuth

    @OneToMany(mappedBy = "refUser", fetch = FetchType.LAZY)
    lateinit var refNotes: List<Note>

    override fun toString(): String {
        return "User(id='$id', name='$name', nameLc='$nameLc', metadata=$metadata, userAuth=$refUserAuth)"
    }

}
