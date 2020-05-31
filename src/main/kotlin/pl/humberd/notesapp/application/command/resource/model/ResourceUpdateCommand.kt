package pl.humberd.notesapp.application.command.resource.model

import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity

data class ResourceUpdateCommand(
    val resourceId: ResourceId,
    val payload: ResourceRevisionEntity.Payload
)
