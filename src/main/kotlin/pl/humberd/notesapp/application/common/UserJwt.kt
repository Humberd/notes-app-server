package pl.humberd.notesapp.application.common

import pl.humberd.notesapp.domain.entity.user.model.UserId

data class UserJwt(
    val userId: UserId
)
