package pl.humberd.notesapp.application.command.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.user.model.UserWithPasswordCredentialsCreateCommand
import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserPasswordCredentials
import pl.humberd.notesapp.domain.entity.user.repository.UserPasswordCredentialsRepository
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import javax.transaction.Transactional

@Service
@Transactional
class UserCommandHandler(
    private val userRepository: UserRepository,
    private val userPasswordCredentialsRepository: UserPasswordCredentialsRepository,
    private val passwordEncoder: PasswordEncoder
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
}
