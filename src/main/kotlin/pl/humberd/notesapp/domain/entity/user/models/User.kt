package pl.humberd.notesapp.domain.entity.user.models

import pl.humberd.notesapp.domain.entity.models.EntityMetadata
import javax.persistence.*

typealias UserId = String

@Entity
@Table(name = "user", schema = "public")
class User(
    @Id
    @Column(name = "id")
    val id: UserId,

    @Column(name = "name")
    var name: String
) {

    @Column(name = "name_lc", updatable = false, insertable = false)
    lateinit var nameLc: String
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "User(id='$id', name='$name', nameLc='$nameLc')"
    }

}
// http://www.vinsguru.com/cloud-design-patterns-materialized-view-pattern-using-spring-boot-postgresql/
