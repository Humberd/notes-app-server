package pl.humberd.notesapp.domain.auth_methods.login_password.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.user.models.UserAuth
import pl.humberd.notesapp.domain.user.models.UserId
import javax.persistence.*

@Entity
@Table(name="LOGIN_PASSWORD_AUTH")
class LoginPasswordAuth(
    @Id
    @Column(name = "user_auth_id")
    val id: UserId,

    @Column(name="email")
    var email: String,

    @Column(name="password_hash")
    var passwordHash: String
) {
    @Embedded
    lateinit var metadata: EntityMetadata

    @OneToOne(mappedBy = "loginPasswordAuth")
    lateinit var userAuth: UserAuth

    override fun toString(): String {
        return "LoginPasswordAuth(id='$id', email='$email', passwordHash='$passwordHash', metadata=$metadata)"
    }

}
