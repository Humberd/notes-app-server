package pl.humberd.notesapp.application.query.user

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.user.repository.UserRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class UserQueryHandler(
    private val userRepository: UserRepository,
    private val userViewMapper: UserViewMapper
) {
    fun view(id: UserId): UserView {
        val user = userRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(user, id)

        return userViewMapper.mapView(user)
    }

    fun minimalView(id: UserId): UserMinimalView {
        val user = userRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(user, id)

        return userViewMapper.mapMinimalView(user)
    }

    fun minimalViewDictionary(ids: Iterable<UserId>): Map<UserId, UserMinimalView> {
        val users = userRepository.findAllById(ids)

        return users.associate { it.id to userViewMapper.mapMinimalView(it) }
    }


}
