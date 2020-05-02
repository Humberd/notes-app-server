package pl.humberd.notesapp.domain.entity.auth_google_provider.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.user.model.UserId
import javax.persistence.*

@Entity
@Table(schema = "public", name = "auth_google_provider")
class GoogleProvider(
    @Id
    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "account_id")
    val accountId: String,

    @Column(name = "account_name")
    val accountName: String,

    @Column(name = "refresh_token")
    val refreshToken: String
)  {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "GoogleProvider(userId='$userId', accountId='$accountId', accountName='$accountName', refreshToken='$refreshToken')"
    }

}
