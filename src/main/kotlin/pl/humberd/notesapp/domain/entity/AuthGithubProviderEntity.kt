package pl.humberd.notesapp.domain.entity

import pl.humberd.notesapp.domain.common.EntityMetadata
import javax.persistence.*

@Entity
@Table(schema = "public", name = "auth_github_provider")
class AuthGithubProviderEntity(
    @Id
    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "id")
    val id: String,

    @Column(name = "login")
    val login: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "avatar_url")
    val avatarUrl: String,

    @Column(name = "access_token")
    val accessToken: String
) {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "AuthGithubProviderEntity(userId='$userId', id='$id', login='$login', name='$name', email='$email', avatarUrl='$avatarUrl')"
    }


}
