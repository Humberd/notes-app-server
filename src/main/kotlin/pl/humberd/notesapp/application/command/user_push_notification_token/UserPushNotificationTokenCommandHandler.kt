package pl.humberd.notesapp.application.command.user_push_notification_token

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.user_push_notification_token.model.UserPushNotificationTokenCreateCommand
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.domain.common.now
import pl.humberd.notesapp.domain.entity.UserPushNotificationTokenEntity
import pl.humberd.notesapp.domain.repository.UserPushNotificationTokenRepository
import kotlin.contracts.ExperimentalContracts

private val logger = KotlinLogging.logger {}

@Service
@Transactional
@ExperimentalContracts
class UserNotificationCommandHandler(
    private val userPushNotificationTokenRepository: UserPushNotificationTokenRepository,
    private val userQueryHandler: UserQueryHandler
) {

    fun create(command: UserPushNotificationTokenCreateCommand): UserPushNotificationTokenEntity {
        userQueryHandler.ASSERT_EXISTS(command.userId)

        val notificationEntity = userPushNotificationTokenRepository.findById(command.token)
        val newNotification = if (notificationEntity.isPresent) {
            notificationEntity.get().userId = command.userId
            notificationEntity.get().created_at = now()
            notificationEntity.get()
        } else {
            userPushNotificationTokenRepository.save(
                UserPushNotificationTokenEntity(
                    token = command.token,
                    userId = command.userId
                )
            )
        }

        return newNotification
    }
}
