package pl.humberd.notesapp.application.command.resource_tag.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.TagId

data class ResourceTagCreateCommand(
    val tagId: TagId,
    val resourceId: ResourceId,
    val omitPublishingToGroupIds: Iterable<GroupId>
)
