package pl.humberd.notesapp.application.query.user.model

import pl.humberd.notesapp.domain.entity.user.model.UserId

data class UserMinimalView(
    val id: UserId,
    val name: String
)

