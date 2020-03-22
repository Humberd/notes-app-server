package pl.humberd.notesapp.infrastructure.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import pl.humberd.notesapp.application.common.UserJwt

class JwtAuthorizationToken(
    val jwt: String,
    val userJwt: UserJwt
) : AbstractAuthenticationToken(emptyList()) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return jwt
    }

    override fun getPrincipal(): Any {
        return userJwt.userId
    }
}
