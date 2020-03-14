package pl.humberd.notesapp.domain.user.models

import java.util.*
import javax.persistence.*

@Entity
class UserAuth(
    @Id
    @Column(name="id")
    val id: String
) {

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar


    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var updatedAt: Calendar

    @OneToOne
    @MapsId
    lateinit var user: User

    override fun toString(): String {
        return "UserAuth(id='$id', createdAt=${createdAt.toInstant()}, updatedAt=${updatedAt.toInstant()})"
    }


}
