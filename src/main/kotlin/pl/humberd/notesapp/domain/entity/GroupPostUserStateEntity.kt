package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "group_post_user_state", schema = "public", catalog = "admin")
@IdClass(GroupPostUserStateEntityPK::class)
class GroupPostUserStateEntity(
    @Id
    @Column(name = "group_post_id")
    var groupPostId: GroupPostId,

    @Id
    @Column(name = "user_id")
    var userId: UserId,

    @Column(name = "resource_revision_id")
    var resourceRevisionId: ResourceRevisionId
) {

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

class GroupPostUserStateEntityPK(
    @Id
    @Column(name = "group_post_id")
    var groupPostId: GroupPostId,

    @Id
    @Column(name = "user_id")
    var userId: UserId
) : java.io.Serializable {

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
