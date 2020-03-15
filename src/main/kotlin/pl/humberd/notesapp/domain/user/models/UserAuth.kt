package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.auth_methods.login_password.models.LoginPasswordAuth
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

    @OneToOne
    @MapsId("user_id")
    @JoinColumn(name="user_id", referencedColumnName = "user_auth_id")
    lateinit var loginPasswordAuth: LoginPasswordAuth

    override fun toString(): String {
        return "UserAuth(id='$id', metadata=$metadata, loginPasswordAuth=$loginPasswordAuth)"
    }
}
