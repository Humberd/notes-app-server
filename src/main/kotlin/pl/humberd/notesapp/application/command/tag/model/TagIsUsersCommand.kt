package pl.humberd.notesapp.application.command.tag.model

import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.entity.UserId

data class TagIsUsersCommand(
    val tagId: TagId,
    val userId: UserId
)
