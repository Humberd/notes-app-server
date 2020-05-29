package pl.humberd.notesapp.application.command.user_push_notification_token.model

import pl.humberd.notesapp.domain.entity.UserId

data class UserPushNotificationTokenCreateCommand(
    val token: String,
    val userId: UserId
)
