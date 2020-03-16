package pl.humberd.notesapp.domain.auth_methods.login_password.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.user.models.UserAuth
import pl.humberd.notesapp.domain.user.models.UserId
import javax.persistence.*

@Entity
@Table(name = "login_password_auth")
class LoginPasswordAuth(
    @Id
    @Column(name = "user_auth_id")
    val userAuthId: UserId,

    @Column(name = "email")
    var email: String,

    @Column(name = "password_hash")
    var passwordHash: String
) {

    @Column(name = "email_lc", updatable = false, insertable = false)
    lateinit var emailLc: String

    @Embedded
    lateinit var metadata: EntityMetadata

    @OneToOne(mappedBy = "refLoginPasswordAuth", fetch = FetchType.LAZY)
    lateinit var refUserAuth: UserAuth

    override fun toString(): String {
        return "LoginPasswordAuth(userAuthId='$userAuthId', email='$email', emailLc='$emailLc' passwordHash='$passwordHash', metadata=$metadata)"
    }

}
