package pl.humberd.notesapp.application.command.auth.github_provider

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.github_provider.model.GithubProviderAuthorizationCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.AuthGithubProviderEntity
import pl.humberd.notesapp.domain.entity.UserEntity
import pl.humberd.notesapp.domain.repository.AuthGithubProviderRepository
import pl.humberd.notesapp.domain.repository.UserRepository

@Service
class GithubProviderCommandHandler(
    private val authGithubProviderRepository: AuthGithubProviderRepository,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {

    fun authorize(command: GithubProviderAuthorizationCommand): String {
        val existingUser = userRepository.findByEmailLc(command.email)
        val user = if (existingUser.isEmpty) {
            userRepository.save(
                UserEntity(
                    id = IdGenerator.random(UserEntity::class),
                    name = command.login,
                    email = command.email
                )
            )
        } else {
            existingUser.get()
        }

        val githubProviderExists = authGithubProviderRepository.existsById(user.id)
        if (!githubProviderExists) {
            authGithubProviderRepository.save(
                AuthGithubProviderEntity(
                    userId = user.id,
                    id = command.id,
                    login = command.login,
                    name = command.name,
                    email = command.email,
                    avatarUrl = command.avatarUrl,
                    accessToken = command.accessToken
                )
            )
        }

        return jwtUtils.generateJwt(
            UserJwt(
                userId = user.id,
                authenticationProvider = AuthenticationProvider.GITHUB
            )
        )
    }
}
