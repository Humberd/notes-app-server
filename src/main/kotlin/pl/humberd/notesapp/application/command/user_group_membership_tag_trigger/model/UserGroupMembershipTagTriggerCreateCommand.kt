package pl.humberd.notesapp.application.command.user_group_membership_tag_trigger.model

import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.entity.UserId

data class UserGroupMembershipTagTriggerCreateCommand(
    val userId: UserId,
    val groupId: GroupId,
    val tagId: TagId
)
