package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
import pl.humberd.notesapp.domain.auth_methods.login_password.models.LoginPasswordAuth
import javax.persistence.*

@Entity
@Table(name = "user_auth")
class UserAuth(
    @Id
    @Column(name = "user_id")
    val userId: UserId
) {

    @Embedded
    lateinit var metadata: EntityMetadata

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId(value = "user_id")
    lateinit var user: User

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_auth_id")
    lateinit var refLoginPasswordAuth: LoginPasswordAuth

    override fun toString(): String {
        return "UserAuth(userId='$userId', metadata=$metadata, refLoginPasswordAuth=$refLoginPasswordAuth)"
    }
}
