package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "group_post_user_state", schema = "public", catalog = "admin")
@IdClass(GroupPostUserStateEntityPK::class)
open class GroupPostUserStateEntity {
    @get:Id
    @get:Column(name = "group_post_id", nullable = false, insertable = false, updatable = false)
    var groupPostId: String? = null

    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "resource_revision_id", nullable = false, insertable = false, updatable = false)
    var resourceRevisionId: String? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "group_post_id", referencedColumnName = "id")
    var refGroupPostEntity: GroupPostEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "resource_revision_id", referencedColumnName = "id")
    var refResourceRevisionEntity: ResourceRevisionEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "groupPostId = $groupPostId " +
                "userId = $userId " +
                "resourceRevisionId = $resourceRevisionId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GroupPostUserStateEntity

        if (groupPostId != other.groupPostId) return false
        if (userId != other.userId) return false
        if (resourceRevisionId != other.resourceRevisionId) return false

        return true
    }

}

class GroupPostUserStateEntityPK : java.io.Serializable {
    @get:Id
    @get:Column(name = "group_post_id", nullable = false, insertable = false, updatable = false)
    var groupPostId: String? = null

    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GroupPostUserStateEntityPK

        if (groupPostId != other.groupPostId) return false
        if (userId != other.userId) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
