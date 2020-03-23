package pl.humberd.notesapp.application.query.tag.model

import pl.humberd.notesapp.domain.entity.tag.model.TagId

data class TagMinimalView(
    val id: TagId,
    val name: String,
    val backgroundColor: String? = null
)
