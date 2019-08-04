package pl.humberd.notesapp.account

import org.springframework.data.annotation.CreatedDate
import java.util.*
import javax.persistence.*

@Entity
data class GoogleAuth(
    @Id
    val id: Long,

    @OneToOne
    @MapsId
    var account: Account,

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Calendar?,


    var userId: String
)
