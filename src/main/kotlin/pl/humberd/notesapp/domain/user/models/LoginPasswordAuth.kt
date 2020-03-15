package pl.humberd.notesapp.domain.user.models

import pl.humberd.notesapp.domain._utils.models.EntityMetadata
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

    @OneToOne
    @MapsId
    lateinit var userAuth: UserAuth
}
