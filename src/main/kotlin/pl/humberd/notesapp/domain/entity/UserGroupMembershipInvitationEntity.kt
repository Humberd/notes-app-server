package pl.humberd.notesapp.domain.entity

import java.sql.Timestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

typealias UserGroupMembershipInvitationId = String

@Entity
@Table(name = "user_group_membership_invitation", schema = "public", catalog = "admin")
class UserGroupMembershipInvitationEntity(
    @Id
    @Column(name = "id")
    val id: UserGroupMembershipInvitationId,

    @Column(name = "group_id")
    val groupId: GroupId,

    @Column(name = "invited_by_user_id")
    val invitedByUserId: UserId,

    @Column(name = "invited_user_id")
    val invitedUserId: UserId
) {

    @Column(name = "created_at", updatable = false, insertable = false)
    var createdAt: Timestamp = Timestamp.from(Calendar.getInstance().toInstant())
        private set


    override fun toString(): String {
        return "UserGroupMembershipInvitation(id='$id', groupId='$groupId', invitedByUserId='$invitedByUserId', invitedUserId='$invitedUserId', createdAt=$createdAt)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserGroupMembershipInvitationEntity

        if (id != other.id) return false
        if (groupId != other.groupId) return false
        if (invitedByUserId != other.invitedByUserId) return false
        if (invitedUserId != other.invitedUserId) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + groupId.hashCode()
        result = 31 * result + invitedByUserId.hashCode()
        result = 31 * result + invitedUserId.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }


}
