package pl.humberd.notesapp.application.command.resource.model

import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.entity.UserId

data class ResourceCreateCommand(
    val authorId: UserId,
    val payload: ResourceRevisionEntity.Payload
)
