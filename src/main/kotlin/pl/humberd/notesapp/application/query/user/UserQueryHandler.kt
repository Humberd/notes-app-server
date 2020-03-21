package pl.humberd.notesapp.application.query.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.NOT_NULL
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class UserQueryHandler(
    private val userRepository: UserRepository
) {
    fun view(id: UserId): UserView {
        val user = userRepository.findByIdOrNull(id)
        NOT_NULL(user, id)

        return mapView(user)
    }

    fun minimalView(id: UserId): UserMinimalView {
        val user = userRepository.findByIdOrNull(id)
        NOT_NULL(user, id)

        return mapMinimalView(user)
    }

    fun minimalViewDictionary(ids: Iterable<UserId>): Map<UserId, UserMinimalView> {
        val users = userRepository.findAllById(ids)

        return users.associate { it.id to mapMinimalView(it) }
    }

    private fun mapMinimalView(user: User): UserMinimalView = UserMinimalView(
        id = user.id,
        name = user.name
    )

    private fun mapView(user: User): UserView = UserView(
        id = user.id,
        name = user.name,
        createdAt = user.metadata.createdAt,
        updatedAt = user.metadata.updatedAt
    )
}
