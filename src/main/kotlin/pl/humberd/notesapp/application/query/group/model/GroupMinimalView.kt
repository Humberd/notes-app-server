package pl.humberd.notesapp.application.query.group.model

import pl.humberd.notesapp.domain.entity.GroupId

data class GroupMinimalView(
    val id: GroupId,
    val name: String,
    val iconUrl: String
)
