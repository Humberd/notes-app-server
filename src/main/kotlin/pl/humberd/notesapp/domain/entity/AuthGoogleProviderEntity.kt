package pl.humberd.notesapp.domain.entity

import pl.humberd.notesapp.domain.common.EntityMetadata
import javax.persistence.*

@Entity
@Table(schema = "public", name = "auth_google_provider")
class AuthGoogleProviderEntity(
    @Id
    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "id")
    val id: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "email")
    val email: String,

    @Column(name = "picture")
    val picture: String,

    @Column(name = "refresh_token")
    val refreshToken: String
)  {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "AuthGoogleProviderEntity(userId='$userId', id='$id', name='$name', picture='$picture')"
    }


}
