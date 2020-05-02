package pl.humberd.notesapp.application.command.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.UserJwt
import pl.humberd.notesapp.domain.common.IdGenerator
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtUtils(
    @Value("\${auth.jwt.secret}")
    private val jwtSecret: String
) {

    fun generateJwt(userJwt: UserJwt): String {
        return Jwts.builder()
            .setClaims(Jwts.claims().also {
                it.id = IdGenerator.random(Jwt::class)
                it.subject = userJwt.userId
                it.issuedAt = Date()
                it.issuer = "notes-app"
                it.audience = "notes-app"
                it.expiration = Date.from(Calendar.getInstance().toInstant().plus(365, ChronoUnit.DAYS))
            })
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()
    }

    fun validateJwt(jwt: String): UserJwt {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(jwt)
            .body

        return UserJwt(
            userId = claims.subject
        )
    }
}
