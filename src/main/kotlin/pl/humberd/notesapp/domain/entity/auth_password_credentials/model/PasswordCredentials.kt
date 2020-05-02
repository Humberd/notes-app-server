package pl.humberd.notesapp.domain.entity.auth_password_credentials.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.user.model.UserId
import javax.persistence.*

@Entity
@Table(name = "auth_password_credentials", schema = "public")
class PasswordCredentials(
    @Id
    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "password_hash")
    var passwordHash: String
) {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "UserPasswordCredentials(userId='$userId', passwordHash='$passwordHash')"
    }

}
