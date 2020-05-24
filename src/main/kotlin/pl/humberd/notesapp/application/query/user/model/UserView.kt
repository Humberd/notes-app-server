package pl.humberd.notesapp.application.query.user.model

import pl.humberd.notesapp.domain.entity.UserId
import java.util.*

data class UserView(
    val id: UserId,
    val name: String,
    val createdAt: Calendar,
    val updatedAt: Calendar
)

