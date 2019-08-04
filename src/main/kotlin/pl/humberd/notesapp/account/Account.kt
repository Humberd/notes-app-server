package pl.humberd.notesapp.account

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Account(
    @OneToOne(cascade = [CascadeType.ALL])
    var googleAuth: GoogleAuth?
) {
    @Id
    @GeneratedValue
    var id: Long = 0

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var createdAt: Calendar

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var lastModifiedAt: Calendar
}
