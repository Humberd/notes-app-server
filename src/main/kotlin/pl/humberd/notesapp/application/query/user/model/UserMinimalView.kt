package pl.humberd.notesapp.application.query.user.model

import pl.humberd.notesapp.domain.entity.UserId

data class UserMinimalView(
    val id: UserId,
    val name: String
)

