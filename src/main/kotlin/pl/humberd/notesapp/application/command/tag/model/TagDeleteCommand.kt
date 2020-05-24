package pl.humberd.notesapp.application.command.tag.model

import pl.humberd.notesapp.domain.entity.TagId

data class TagDeleteCommand(
    val tagId: TagId
)
