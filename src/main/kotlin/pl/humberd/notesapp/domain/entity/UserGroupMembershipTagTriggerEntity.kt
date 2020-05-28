package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "user_group_membership_tag_trigger", schema = "public", catalog = "admin")
@IdClass(UserGroupMembershipTagTriggerEntityPK::class)
open class UserGroupMembershipTagTriggerEntity(
    @Id
    @Column(name = "user_id")
    var userId: UserId,

    @Id
    @Column(name = "group_id")
    var groupId: GroupId,

    @Id
    @Column(name = "tag_id")
    var tagId: TagId
) {

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
    @Id
    @Column(name = "user_id")
    lateinit var userId: String

    @Id
    @Column(name = "group_id")
    lateinit var groupId: String

    @Id
    @Column(name = "tag_id")
    lateinit var tagId: String

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
