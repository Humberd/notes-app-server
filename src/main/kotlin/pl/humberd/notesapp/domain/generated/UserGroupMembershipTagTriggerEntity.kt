package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "user_group_membership_tag_trigger", schema = "public", catalog = "admin")
@IdClass(UserGroupMembershipTagTriggerEntityPK::class)
open class UserGroupMembershipTagTriggerEntity {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Id
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: String? = null

    @get:Id
    @get:Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    var tagId: String? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    var refGroupEntity: GroupEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "tag_id", referencedColumnName = "id")
    var refTagEntity: TagEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "userId = $userId " +
                "groupId = $groupId " +
                "tagId = $tagId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserGroupMembershipTagTriggerEntity

        if (userId != other.userId) return false
        if (groupId != other.groupId) return false
        if (tagId != other.tagId) return false

        return true
    }

}

class UserGroupMembershipTagTriggerEntityPK : java.io.Serializable {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Id
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: String? = null

    @get:Id
    @get:Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    var tagId: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserGroupMembershipTagTriggerEntityPK

        if (userId != other.userId) return false
        if (groupId != other.groupId) return false
        if (tagId != other.tagId) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
