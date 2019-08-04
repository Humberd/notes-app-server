package pl.humberd.notesapp.account

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*

@Entity
data class Account(
    @Id
    @GeneratedValue
    val id: Long,

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Calendar?,

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var lastModifiedAt: Calendar?,

    @OneToOne(cascade = [CascadeType.ALL])
    var googleAuth: GoogleAuth?
)
