package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import javax.persistence.*

@Entity
@Table(name = "USER_AUTH")
class UserAuth(
    @Id
    @Column(name = "user_id")
    val id: UserId
) {

    @Embedded
    lateinit var metadata: EntityMetadata

    @OneToOne
    @MapsId
    lateinit var user: User

    @OneToOne(mappedBy = "userAuth")
    lateinit var loginPasswordAuth: LoginPasswordAuth;

    override fun toString(): String {
        return "UserAuth(id='$id', metadata=$metadata)"
    }
}
