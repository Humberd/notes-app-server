package pl.humberd.notesapp.application.query.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.user.models.User
import pl.humberd.notesapp.domain.entity.user.models.UserId
import pl.humberd.notesapp.domain.entity.user.services.UserRepository
import pl.humberd.notesapp.domain.exceptions.NotFoundError

@Service
class UserQueryHandler(
    private val userRepository: UserRepository
) {
    fun minimalView(id: UserId): UserMinimalView {
        val user = userRepository.findByIdOrNull(id)
        if (user === null) {
            throw NotFoundError(User::class, id)
        }

        return mapMinimalView(user)
    }

    fun minimalViewMap(ids: Iterable<UserId>): Map<UserId, UserMinimalView> {
        val users = userRepository.findAllById(ids)

        return users.associate { it.id to mapMinimalView(it) }
    }

    private fun mapMinimalView(user: User): UserMinimalView = UserMinimalView(
        id = user.id,
        name = user.name
    )
}
