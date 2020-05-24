package pl.humberd.notesapp.application.query.tag.model

import pl.humberd.notesapp.domain.entity.TagId
import java.util.*

data class TagView(
    val id: TagId,
    val name: String,
    val backgroundColor: String?,
    val notesCount: Int,
    val createdAt: Calendar,
    val updatedAt: Calendar
)
