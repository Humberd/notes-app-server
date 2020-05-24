package pl.humberd.notesapp.application.command.auth

import pl.humberd.notesapp.domain.entity.UserId

enum class AuthenticationProvider {
    PASSWORD_CREDENTIALS,
    GOOGLE,
    GITHUB
}


data class UserJwt(
    val userId: UserId,
    val authenticationProvider: AuthenticationProvider
)
