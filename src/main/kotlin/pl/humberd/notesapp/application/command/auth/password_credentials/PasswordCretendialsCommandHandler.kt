package pl.humberd.notesapp.application.command.auth.password_credentials

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsLoginCommand
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsLoginMobileCommand
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsRegisterCommand
import pl.humberd.notesapp.application.command.user_push_notification_token.UserNotificationCommandHandler
import pl.humberd.notesapp.application.command.user_push_notification_token.model.UserPushNotificationTokenCreateCommand
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST_GENERIC
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.UnauthorizedException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.AuthPasswordCredentialsEntity
import pl.humberd.notesapp.domain.entity.UserEntity
import pl.humberd.notesapp.domain.repository.AuthPasswordCredentialsRepository
import pl.humberd.notesapp.domain.repository.UserRepository
import java.util.*
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class PasswordCretendialsCommandHandler(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val authPasswordCredentialsRepository: AuthPasswordCredentialsRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userNotificationCommandHandler: UserNotificationCommandHandler
) {

    fun register(command: PasswordCredentialsRegisterCommand): UserEntity {
        val userExists = userRepository.existsByNameLc(command.name.toLowerCase())
        ASSERT_NOT_EXIST_GENERIC<UserEntity>(
            userExists,
            command.name.toLowerCase()
        )

        val emailExists = userRepository.existsByEmailLc(command.email.toLowerCase())
        ASSERT_NOT_EXIST_GENERIC<UserEntity>(
            emailExists,
            command.email.toLowerCase()
        )

        val user = userRepository.save(
            UserEntity(
                id = IdGenerator.random(UserEntity::class),
                name = command.name,
                email = command.email
            )
        )

        authPasswordCredentialsRepository.save(
            AuthPasswordCredentialsEntity(
                userId = user.id,
                passwordHash = passwordEncoder.encode(command.password)
            )
        )

        userRepository.saveFlushRefresh(user)

        return user
    }


    fun login(command: PasswordCredentialsLoginCommand): String {
        val user = userRepository.findByEmailLc(command.email.toLowerCase())
        ASSERT_NOT_NULL(
            user.orElseGet(null),
            command.email.toLowerCase()
        )

        val userPasswordCredentials = authPasswordCredentialsRepository.findById(user.get().id)

        val isAuthorized = isAuthorized(userPasswordCredentials, command.password)
        if (!isAuthorized) {
            throw UnauthorizedException()
        }

        return jwtUtils.generateJwt(
            UserJwt(
                userId = userPasswordCredentials.get().userId,
                authenticationProvider = AuthenticationProvider.PASSWORD_CREDENTIALS
            )
        )
    }

    fun loginMobile(command: PasswordCredentialsLoginMobileCommand): String {
        val jwt = login(
            PasswordCredentialsLoginCommand(
                email = command.email,
                password = command.password
            )
        )

        val user = userRepository.findByEmailLc(command.email.toLowerCase())
        ASSERT_NOT_NULL(
            user.orElseGet(null),
            command.email.toLowerCase()
        )

        userNotificationCommandHandler.create(
            UserPushNotificationTokenCreateCommand(
                userId = user.get().id,
                token = command.pushToken
            )
        )

        return jwt
    }

    private fun isAuthorized(
        passwordCredentials: Optional<AuthPasswordCredentialsEntity>,
        password: String
    ): Boolean {
        if (passwordCredentials.isEmpty) {
            return false
        }

        return passwordEncoder.matches(password, passwordCredentials.get().passwordHash)
    }


}
