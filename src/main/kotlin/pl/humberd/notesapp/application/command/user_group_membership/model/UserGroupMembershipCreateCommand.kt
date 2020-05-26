package pl.humberd.notesapp.application.command.user_group_membership.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.UserId

data class UserGroupMembershipCreateCommand(
    val groupId: GroupId,
    val userId: UserId
)
