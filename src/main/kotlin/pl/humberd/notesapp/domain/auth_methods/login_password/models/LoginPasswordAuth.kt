package pl.humberd.notesapp.domain.auth_methods.login_password.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.user.models.UserId
import javax.persistence.*

@Entity
@Table(name = "login_password_auth")
class LoginPasswordAuth(
    @Id
    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "email")
    var email: String,

    @Column(name = "password_hash")
    var passwordHash: String
) {

    @Column(name = "email_lc", updatable = false, insertable = false)
    lateinit var emailLc: String

    @Embedded
    lateinit var metadata: EntityMetadata

    override fun toString(): String {
        return "LoginPasswordAuth(userId='$userId', email='$email', emailLc='$emailLc' passwordHash='$passwordHash')"
    }

}
