package pl.humberd.notesapp.domain.entity

import pl.humberd.notesapp.domain.common.EntityMetadata
import javax.persistence.*

@Entity
@Table(name = "auth_password_credentials", schema = "public")
class AuthPasswordCredentialsEntity(
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
        return "AuthPasswordCredentialsEntity(userId='$userId', passwordHash='$passwordHash')"
    }

}
