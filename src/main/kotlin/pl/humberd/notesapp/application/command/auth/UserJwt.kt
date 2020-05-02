package pl.humberd.notesapp.application.command.auth

import pl.humberd.notesapp.application.exceptions.NotFoundException
import pl.humberd.notesapp.domain.entity.user.model.UserId

enum class AuthenticationProvider {
    PASSWORD_CREDENTIALS,
    GOOGLE;

    companion object {
        fun fromOathProvider(value: String): AuthenticationProvider {
            return when (value) {
                "google" -> GOOGLE
                else -> throw NotFoundException(AuthenticationProvider::class, value)
            }
        }

        fun isOauthProvider(value: String): Boolean {
            return try {
                fromOathProvider(value)
                true
            } catch (e: NotFoundException) {
                false
            }
        }
    }
}


data class UserJwt(
    val userId: UserId,
    val authenticationProvider: AuthenticationProvider
)
