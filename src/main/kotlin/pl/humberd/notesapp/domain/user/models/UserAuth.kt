package pl.humberd.notesapp.domain.user.models

import java.util.*
import javax.persistence.*

@Entity
class UserAuth(
    @Id
    @Column(name="user_id")
    val userId: String
) {

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar


    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var updatedAt: Calendar

//    @OneToOne(mappedBy = "id")
//    lateinit var user: User

}
