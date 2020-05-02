package pl.humberd.notesapp.application.command.auth.google_provider

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.google_provider.model.GoogleProviderAuthorizationCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.auth_google_provider.model.GoogleProvider
import pl.humberd.notesapp.domain.entity.auth_google_provider.repository.GoogleProviderRepository
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class GoogleProviderCommandHandler(
    private val googleProviderRepository: GoogleProviderRepository,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    fun authorize(command: GoogleProviderAuthorizationCommand): String {
        val existingUser = userRepository.findByEmailLc(command.email)
        val user = if (existingUser.isEmpty) {
            userRepository.save(
                User(
                    id = IdGenerator.random(User::class),
                    name = command.accountName,
                    email = command.email
                )
            )
        } else {
            existingUser.get()
        }

        val googleProviderExists = googleProviderRepository.existsById(user.id)
        if (!googleProviderExists) {
            googleProviderRepository.save(
                GoogleProvider(
                    userId = user.id,
                    accountId = command.accountId,
                    accountName = command.accountName,
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
