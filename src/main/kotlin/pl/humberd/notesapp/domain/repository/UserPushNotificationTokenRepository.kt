package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.UserId
import pl.humberd.notesapp.domain.entity.UserPushNotificationTokenEntity

@Repository
interface UserPushNotificationTokenRepository : RefreshableJpaRepository<UserPushNotificationTokenEntity, String> {
    fun findAllByUserId(userId: UserId): List< UserPushNotificationTokenEntity>
}
