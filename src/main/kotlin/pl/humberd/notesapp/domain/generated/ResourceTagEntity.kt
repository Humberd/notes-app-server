package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "resource_tag", schema = "public", catalog = "admin")
@IdClass(ResourceTagEntityPK::class)
open class ResourceTagEntity {
    @get:Id
    @get:Column(name = "resource_id", nullable = false, insertable = false, updatable = false)
    var resourceId: String? = null

    @get:Id
    @get:Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    var tagId: String? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "resource_id", referencedColumnName = "id")
    var refResourceEntity: ResourceEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "tag_id", referencedColumnName = "id")
    var refTagEntity: TagEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "resourceId = $resourceId " +
                "tagId = $tagId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceTagEntity

        if (resourceId != other.resourceId) return false
        if (tagId != other.tagId) return false

        return true
    }

}

class ResourceTagEntityPK : java.io.Serializable {
    @get:Id
    @get:Column(name = "resource_id", nullable = false, insertable = false, updatable = false)
    var resourceId: String? = null

    @get:Id
    @get:Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    var tagId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceTagEntityPK

        if (resourceId != other.resourceId) return false
        if (tagId != other.tagId) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
