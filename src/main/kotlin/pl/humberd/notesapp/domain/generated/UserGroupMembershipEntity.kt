package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "user_group_membership", schema = "public", catalog = "admin")
@IdClass(UserGroupMembershipEntityPK::class)
open class UserGroupMembershipEntity {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Id
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: String? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "group_id", referencedColumnName = "id")
    var refGroupEntity: GroupEntity? = null

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
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Id
    @get:Column(name = "group_id", nullable = false, insertable = false, updatable = false)
    var groupId: String? = null

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
