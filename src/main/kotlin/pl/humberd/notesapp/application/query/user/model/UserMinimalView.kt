package pl.humberd.notesapp.application.query.user.model

import pl.humberd.notesapp.domain.entities.user.models.UserId

data class UserMinimalView(
    val id: UserId,
    val name: String
)

