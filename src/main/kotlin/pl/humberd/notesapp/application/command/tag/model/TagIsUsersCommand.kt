package pl.humberd.notesapp.application.command.tag.model

import pl.humberd.notesapp.domain.entity.tag.model.TagId
import pl.humberd.notesapp.domain.entity.user.model.UserId

data class TagIsUsersCommand(
    val tagId: TagId,
    val userId: UserId
)
