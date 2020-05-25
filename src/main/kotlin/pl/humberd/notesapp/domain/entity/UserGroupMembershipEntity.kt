package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "user_group_membership", schema = "public", catalog = "admin")
@IdClass(UserGroupMembershipEntityPK::class)
class UserGroupMembershipEntity(
    @Id
    @Column(name = "user_id")
    var userId: UserId,

    @Id
    @Column(name = "group_id")
    var groupId: GroupId
) {


    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "userId = $userId " +
                "groupId = $groupId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserGroupMembershipEntity

        if (userId != other.userId) return false
        if (groupId != other.groupId) return false

        return true
    }

}

class UserGroupMembershipEntityPK : java.io.Serializable {
    @Id
    @Column(name = "user_id")
    lateinit var userId: String

    @Id
    @Column(name = "group_id")
    lateinit var groupId: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserGroupMembershipEntityPK

        if (userId != other.userId) return false
        if (groupId != other.groupId) return false

        return true
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}
