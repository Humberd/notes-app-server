package pl.humberd.notesapp.application.command.auth.password_credentials

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsLoginCommand
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsRegisterCommand
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.UnauthorizedException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.auth_password_credentials.model.PasswordCredentials
import pl.humberd.notesapp.domain.entity.auth_password_credentials.repository.PasswordCredentialsRepository
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import java.util.*
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class PasswordCretendialsCommandHandler(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository,
    private val passwordCredentialsRepository: PasswordCredentialsRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(command: PasswordCredentialsRegisterCommand): User {
        val userExists = userRepository.existsByNameLc(command.name.toLowerCase())
        ASSERT_NOT_EXIST<User>(userExists, command.name.toLowerCase())

        val emailExists = userRepository.existsByEmailLc(command.email.toLowerCase())
        ASSERT_NOT_EXIST<User>(emailExists, command.email.toLowerCase())

        val user = userRepository.save(
            User(
                id = IdGenerator.random(User::class),
                name = command.name,
                email = command.email
            )
        )

        passwordCredentialsRepository.save(
            PasswordCredentials(
                userId = user.id,
                passwordHash = passwordEncoder.encode(command.password)
            )
        )

        userRepository.saveFlushRefresh(user)

        return user
    }


    fun login(command: PasswordCredentialsLoginCommand): String {
        val user = userRepository.findByEmailLc(command.email.toLowerCase())
        ASSERT_NOT_NULL(user.orElseGet(null), command.email.toLowerCase())

        val userPasswordCredentials = passwordCredentialsRepository.findById(user.get().id)

        val isAuthorized = isAuthorized(userPasswordCredentials, command.password)
        if (!isAuthorized) {
            throw UnauthorizedException()
        }

        return jwtUtils.generateJwt(
            UserJwt(
                userId = userPasswordCredentials.get().userId,
                authenticationProvider = AuthenticationProvider.GOOGLE
            )
        )
    }

    private fun isAuthorized(
        passwordCredentials: Optional<PasswordCredentials>,
        password: String
    ): Boolean {
        if (passwordCredentials.isEmpty) {
            return false
        }

        return passwordEncoder.matches(password, passwordCredentials.get().passwordHash)
    }


}
