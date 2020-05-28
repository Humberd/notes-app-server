package pl.humberd.notesapp.application.query.user.model

import pl.humberd.notesapp.domain.entity.UserId
import java.sql.Timestamp

data class UserView(
    val id: UserId,
    val name: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
)

