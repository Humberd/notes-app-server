package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "resource_upvote", schema = "public", catalog = "admin")
@IdClass(ResourceUpvoteEntityPK::class)
open class ResourceUpvoteEntity(
    @Id
    @Column(name = "resource_id")
    var resourceId: ResourceId,

    @Id
    @Column(name = "user_id")
    var userId: UserId
) {

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "resourceId = $resourceId " +
                "userId = $userId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceUpvoteEntity

        if (resourceId != other.resourceId) return false
        if (userId != other.userId) return false

        return true
    }

}

class ResourceUpvoteEntityPK : java.io.Serializable {
    @Id
    @Column(name = "resource_id", nullable = false, insertable = false, updatable = false)
    var resourceId: String? = null

    @Id
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceUpvoteEntityPK

        if (resourceId != other.resourceId) return false
        if (userId != other.userId) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
