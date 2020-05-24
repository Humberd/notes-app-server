package pl.humberd.notesapp.domain.entity

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

typealias ResourceRevisionId = String

@Entity
@Table(name = "resource_revision", schema = "public", catalog = "admin")
@TypeDefs(
    TypeDef(name = "json", typeClass = JsonStringType::class),
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
)
class ResourceRevisionEntity(
    @Id
    @Column(name = "id")
    var id: ResourceRevisionId,

    @Column(name = "resource_id")
    var resourceId: ResourceId,

    @Column(name = "change_kind")
    var changeKind: String,

    @Column(name = "type")
    var type: String,

    @Type(type = "jsonb")
    @Column(name = "payload", columnDefinition = "jsonb")
    var payload: Any
) {

    @Column(name = "created_at")
    lateinit var createdAt: java.sql.Timestamp

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "resourceId = $resourceId " +
                "changeKind = $changeKind " +
                "type = $type " +
                "payload = $payload " +
                "createdAt = $createdAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceRevisionEntity

        if (id != other.id) return false
        if (resourceId != other.resourceId) return false
        if (changeKind != other.changeKind) return false
        if (type != other.type) return false
        if (payload != other.payload) return false
        if (createdAt != other.createdAt) return false

        return true
    }

}

