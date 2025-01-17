package pl.humberd.notesapp.application.command.tag.model

import pl.humberd.notesapp.domain.entity.user.model.UserId

data class TagCreateCommand(
    val userId: UserId,
    val name: String,
    val backgoundColor: String? = null
)
