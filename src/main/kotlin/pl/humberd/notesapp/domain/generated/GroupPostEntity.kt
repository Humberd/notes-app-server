package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "group_post", schema = "public", catalog = "admin")
open class GroupPostEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: String? = null

    @get:Basic
    @get:Column(name = "resource_id", nullable = false, insertable = false, updatable = false)
    var resourceId: String? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    var refGroupEntity: GroupEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "resource_id", referencedColumnName = "id")
    var refResourceEntity: ResourceEntity? = null

    @get:OneToMany(mappedBy = "refGroupPostEntity")
    var refGroupPostUserStateEntities: List<GroupPostUserStateEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "groupId = $groupId " +
                "resourceId = $resourceId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GroupPostEntity

        if (id != other.id) return false
        if (groupId != other.groupId) return false
        if (resourceId != other.resourceId) return false

        return true
    }

}

