package pl.humberd.notesapp.application.command.resource_revision.model

import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity

data class ResourceRevisionCreateCommand(
    val resourceId: ResourceId,
    val changeKind: ResourceRevisionEntity.ResourceChangeKind,
    val payload: ResourceRevisionEntity.Payload
)
