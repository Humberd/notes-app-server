package pl.humberd.notesapp.application.command.auth.github_provider

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.AuthenticationProvider
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.UserJwt
import pl.humberd.notesapp.application.command.auth.github_provider.model.GithubProviderAuthorizationCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.auth_github_provider.model.GithubProvider
import pl.humberd.notesapp.domain.entity.auth_github_provider.repository.GithubProviderRepository
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository

@Service
class GithubProviderCommandHandler(
    private val githubProviderRepository: GithubProviderRepository,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {

    fun authorize(command: GithubProviderAuthorizationCommand): String {
        val existingUser = userRepository.findByEmailLc(command.email)
        val user = if (existingUser.isEmpty) {
            userRepository.save(
                User(
                    id = IdGenerator.random(User::class),
                    name = command.login,
                    email = command.email
                )
            )
        } else {
            existingUser.get()
        }

        val githubProviderExists = githubProviderRepository.existsById(user.id)
        if (!githubProviderExists) {
            githubProviderRepository.save(
                GithubProvider(
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
