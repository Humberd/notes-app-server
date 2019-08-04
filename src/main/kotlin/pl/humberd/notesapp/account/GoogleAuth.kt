package pl.humberd.notesapp.account

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class GoogleAuth(
    @OneToOne
    var account: Account,

    @Id
    var id: String,

    var email: String
) {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar
}
