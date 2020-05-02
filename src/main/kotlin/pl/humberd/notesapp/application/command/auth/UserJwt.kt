package pl.humberd.notesapp.application.command.auth

import pl.humberd.notesapp.domain.entity.user.model.UserId

enum class AuthenticationProvider {
    PASSWORD_CREDENTIALS,
    GOOGLE;
}


data class UserJwt(
    val userId: UserId,
    val authenticationProvider: AuthenticationProvider
)
