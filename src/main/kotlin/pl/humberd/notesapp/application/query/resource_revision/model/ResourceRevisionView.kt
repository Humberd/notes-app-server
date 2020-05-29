package pl.humberd.notesapp.application.query.resource_revision.model

import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionId
import java.sql.Timestamp

data class ResourceRevisionView(
    val id: ResourceRevisionId,
    val resourceId: ResourceId,
    val changeKind: ResourceRevisionEntity.ResourceChangeKind,
    val type: ResourceRevisionEntity.ResourceType,
    val payload: ResourceRevisionEntity.Payload,
    val createdAt: Timestamp
)
