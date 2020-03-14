package pl.humberd.notesapp.domain.user.models

import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "name")
    var name: String
) {

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar


    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var updatedAt: Calendar

//    @OneToOne
//    lateinit var userAuth: UserAuth

    override fun toString(): String {
        return "User(id='$id', name='$name', createdAt=${createdAt.toInstant()}, updatedAt=${updatedAt.toInstant()})"
    }
}
