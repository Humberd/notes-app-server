package pl.humberd.notesapp.application.command.group_post.model

import pl.humberd.notesapp.domain.entity.GroupPostId
import pl.humberd.notesapp.domain.entity.UserId

data class GroupPostSetLatestRevisionCommand(
    val groupPostId: GroupPostId,
    val userId: UserId
)