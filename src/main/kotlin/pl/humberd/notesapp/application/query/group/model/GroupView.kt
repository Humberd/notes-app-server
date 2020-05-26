package pl.humberd.notesapp.application.query.group.model

import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.GroupId

data class GroupView(
    val id: GroupId,
    val name: String,
    val iconUrl: String,
    val members: Iterable<UserMinimalView>
)
