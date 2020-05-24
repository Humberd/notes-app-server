package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "resource_revision", schema = "public", catalog = "admin")
open class ResourceRevisionEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "resource_id", nullable = false, insertable = false, updatable = false)
    var resourceId: String? = null

    @get:Basic
    @get:Column(name = "change_kind", nullable = false)
    var changeKind: Any? = null

    @get:Basic
    @get:Column(name = "type", nullable = false)
    var type: Any? = null

    @get:Basic
    @get:Column(name = "payload", nullable = false)
    var payload: Any? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:OneToMany(mappedBy = "refResourceRevisionEntity")
    var refGroupPostUserStateEntities: List<GroupPostUserStateEntity>? = null

    @get:OneToMany(mappedBy = "refResourceRevisionEntity")
    var refResourceEntities: List<ResourceEntity>? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "resource_id", referencedColumnName = "id")
    var refResourceEntity: ResourceEntity? = null

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

