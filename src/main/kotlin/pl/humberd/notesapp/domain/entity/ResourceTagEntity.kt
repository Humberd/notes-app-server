package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "resource_tag", schema = "public", catalog = "admin")
@IdClass(ResourceTagEntityPK::class)
open class ResourceTagEntity(
    @Id
    @Column(name = "resource_id")
    var resourceId: ResourceId,

    @Id
    @Column(name = "tag_id")
    var tagId: TagId
) {

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
    @Id
    @Column(name = "resource_id")
    lateinit var resourceId: ResourceId

    @Id
    @Column(name = "tag_id")
    lateinit var tagId: TagId

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
