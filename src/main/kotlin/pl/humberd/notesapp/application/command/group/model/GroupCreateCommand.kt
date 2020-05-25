package pl.humberd.notesapp.application.command.group.model

import pl.humberd.notesapp.domain.entity.UserId

data class GroupCreateCommand(
    val name: String,
    val iconUrl: String,
    val ownerId: UserId
)
