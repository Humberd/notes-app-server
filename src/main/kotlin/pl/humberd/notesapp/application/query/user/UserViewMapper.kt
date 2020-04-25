package pl.humberd.notesapp.application.query.user

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.domain.entity.user.model.User

@Service
class UserViewMapper {
    fun mapView(user: User): UserView = UserView(
        id = user.id,
        name = user.name,
        createdAt = user.metadata.createdAt,
        updatedAt = user.metadata.updatedAt
    )

    fun mapMinimalView(user: User): UserMinimalView = UserMinimalView(
        id = user.id,
        name = user.name
    )
}
