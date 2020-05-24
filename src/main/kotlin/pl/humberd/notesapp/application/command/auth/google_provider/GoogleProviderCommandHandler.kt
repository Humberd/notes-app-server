package pl.humberd.notesapp.application.command.auth.google_provider

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.google_provider.model.GoogleProviderAuthorizationCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.AuthGoogleProviderEntity
import pl.humberd.notesapp.domain.entity.UserEntity
import pl.humberd.notesapp.domain.repository.AuthGoogleProviderRepository
import pl.humberd.notesapp.domain.repository.UserRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class GoogleProviderCommandHandler(
    private val authGoogleProviderRepository: AuthGoogleProviderRepository,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    fun authorize(command: GoogleProviderAuthorizationCommand): String {
        val existingUser = userRepository.findByEmailLc(command.email)
        val user = if (existingUser.isEmpty) {
            userRepository.save(
                UserEntity(
                    id = IdGenerator.random(UserEntity::class),
                    name = command.name,
                    email = command.email
                )
            )
        } else {
            existingUser.get()
        }

        val googleProviderExists = authGoogleProviderRepository.existsById(user.id)
        if (!googleProviderExists) {
            authGoogleProviderRepository.save(
                AuthGoogleProviderEntity(
                    userId = user.id,
                    id = command.id,
                    name = command.name,
                    email = command.email,
                    picture = command.picture,
                    refreshToken = command.refreshToken
                )
            )
        }

        return jwtUtils.generateJwt(
            UserJwt(
                userId = user.id,
                authenticationProvider = AuthenticationProvider.GOOGLE
            )
        )
    }
}
