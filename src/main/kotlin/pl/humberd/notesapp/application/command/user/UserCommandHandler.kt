package pl.humberd.notesapp.application.command.user

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.user.model.PasswordCredentialsAuthorizationCommand
import pl.humberd.notesapp.application.command.user.model.UserWithPasswordCredentialsCreateCommand
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.application.exceptions.UnauthorizedException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.user.model.UserPasswordCredentials
import pl.humberd.notesapp.domain.entity.user.repository.UserPasswordCredentialsRepository
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import java.util.*
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
@Transactional
class UserCommandHandler(
    private val userRepository: UserRepository,
    private val userPasswordCredentialsRepository: UserPasswordCredentialsRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${auth.jwt.secret}")
    private val jwtSecret: String
) {

    fun create(command: UserWithPasswordCredentialsCreateCommand): User {
        val userExists = userRepository.existsByNameLc(command.name.toLowerCase())
        if (userExists) {
            throw AlreadyExistsException(User::class, command.name)
        }

        val emailExists = userPasswordCredentialsRepository.existsByEmailLc(command.email.toLowerCase())
        if (emailExists) {
            throw AlreadyExistsException(UserPasswordCredentials::class, command.email)
        }

        val user = userRepository.save(
            User(
                id = IdGenerator.random(User::class),
                name = command.name
            )
        )

        userPasswordCredentialsRepository.save(
            UserPasswordCredentials(
                userId = user.id,
                email = command.email,
                passwordHash = passwordEncoder.encode(command.password)
            )
        )

        userRepository.flush()
        userRepository.refresh(user)
        return user
    }

    fun authorize(command: PasswordCredentialsAuthorizationCommand): String {
        println(jwtSecret)
        val userPasswordCredentials = userPasswordCredentialsRepository.findByEmailLc(command.email.toLowerCase())

        val isAuthorized = isAuthorized(userPasswordCredentials, command)
        if (!isAuthorized) {
            throw UnauthorizedException()
        }

        return generateJwt(userPasswordCredentials.get().userId)
    }

    private fun generateJwt(userId: UserId): String {
        return Jwts.builder()
            .setClaims(Jwts.claims())
            .setClaims(mapOf("userId" to userId))
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact()
    }

    private fun isAuthorized(
        userPasswordCredentials: Optional<UserPasswordCredentials>,
        command: PasswordCredentialsAuthorizationCommand
    ): Boolean {
        if (userPasswordCredentials.isEmpty) {
            return false
        }

        return passwordEncoder.matches(command.password, userPasswordCredentials.get().passwordHash)
    }
}
