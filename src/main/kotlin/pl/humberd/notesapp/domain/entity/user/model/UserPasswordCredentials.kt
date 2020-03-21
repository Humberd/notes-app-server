package pl.humberd.notesapp.domain.entity.user.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import javax.persistence.*

@Entity
@Table(name = "user_password_credentials")
class UserPasswordCredentials(
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
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "UserPasswordCredentials(userId='$userId', email='$email', emailLc='$emailLc' passwordHash='$passwordHash')"
    }

}
