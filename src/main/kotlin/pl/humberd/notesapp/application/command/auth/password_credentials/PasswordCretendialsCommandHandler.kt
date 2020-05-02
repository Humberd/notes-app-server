package pl.humberd.notesapp.application.command.auth.password_credentials

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsLoginCommand
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsRegisterCommand
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.application.exceptions.UnauthorizedException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserPasswordCredentials
import pl.humberd.notesapp.domain.entity.user.repository.UserPasswordCredentialsRepository
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
    private val userPasswordCredentialsRepository: UserPasswordCredentialsRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(command: PasswordCredentialsRegisterCommand): User {
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

        userRepository.saveFlushRefresh(user)

        return user
    }


    fun login(command: PasswordCredentialsLoginCommand): String {
        val userPasswordCredentials = userPasswordCredentialsRepository.findByEmailLc(command.email.toLowerCase())

        val isAuthorized = isAuthorized(userPasswordCredentials, command.password)
        if (!isAuthorized) {
            throw UnauthorizedException()
        }

        return jwtUtils.generateJwt(UserJwt(userId = userPasswordCredentials.get().userId))
    }

    private fun isAuthorized(
        userPasswordCredentials: Optional<UserPasswordCredentials>,
        password: String
    ): Boolean {
        if (userPasswordCredentials.isEmpty) {
            return false
        }

        return passwordEncoder.matches(password, userPasswordCredentials.get().passwordHash)
    }


}
