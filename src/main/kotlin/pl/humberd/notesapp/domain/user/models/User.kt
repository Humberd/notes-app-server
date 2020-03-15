package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.note.models.Note
import javax.persistence.*

@Entity
@Table(name="USER")
class User(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "name")
    var name: String
) {

    @Embedded
    lateinit var metadata: EntityMetadata

    @OneToOne(mappedBy = "user")
    lateinit var userAuth: UserAuth

    @OneToMany(mappedBy = "user")
    lateinit var notes: List<Note>

    override fun toString(): String {
        return "User(id='$id', name='$name', metadata=$metadata, userAuth=$userAuth)"
    }

}
