package pl.humberd.notesapp.application.command.group_post_user_state.model

import pl.humberd.notesapp.domain.entity.GroupPostId
import pl.humberd.notesapp.domain.entity.ResourceRevisionId
import pl.humberd.notesapp.domain.entity.UserId

data class GroupPostUserStateUpdateCommand(
    val groupPostId: GroupPostId,
    val userId: UserId,
    val newResourceRevisionId: ResourceRevisionId
)