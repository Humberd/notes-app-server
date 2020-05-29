package pl.humberd.notesapp.domain.entity

import pl.humberd.notesapp.domain.common.now
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_push_notification_token", schema = "public")
class UserPushNotificationTokenEntity(
    @Id
    @Column(name = "token")
    val token: String,

    @Column(name = "user_id")
    var userId: UserId
) {

    @Column(name = "created_at", insertable = false)
    var created_at = now()

    override fun toString(): String {
        return "UserNotificationEntity(user_id='$userId', token='$token', created_at=$created_at)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPushNotificationTokenEntity

        if (userId != other.userId) return false
        if (token != other.token) return false
        if (created_at != other.created_at) return false

        return true
    }

    override fun hashCode() = 42

}
