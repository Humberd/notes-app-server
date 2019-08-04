package pl.humberd.notesapp.account

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class GoogleAuth(
    @OneToOne
    @MapsId
    var account: Account,

    var userId: String
) {
    @Id
    var id: Long = 0

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar
}
