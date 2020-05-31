package pl.humberd.notesapp.application.command.group_post.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceRevisionId

data class GroupPostCreateCommand(
    val groupId: GroupId,
    val resourceId: ResourceId,
    val resourceRevisionId: ResourceRevisionId
)
