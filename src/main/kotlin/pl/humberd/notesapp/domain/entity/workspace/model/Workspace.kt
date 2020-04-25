package pl.humberd.notesapp.domain.entity.workspace.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.user.model.UserId
import javax.persistence.*

typealias WorkspaceId = String

@Entity
@Table(name = "workspace", schema = "public")
class Workspace(
    @Id
    @Column(name = "id")
    val id: WorkspaceId,

    @Column(name = "user_id")
    val userId: UserId,

    @Column(name = "name")
    var name: String
) {

    @Column(name = "name_lc", updatable = false, insertable = false)
    var nameLc: String? = null
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "Workspace(id='$id', userId='$userId', name='$name')"
    }
}
