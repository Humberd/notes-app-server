package pl.humberd.notesapp.application.query.tag.model

import pl.humberd.notesapp.domain.entity.TagId
import java.sql.Timestamp

data class TagView(
    val id: TagId,
    val name: String,
    val backgroundColor: String?,
    val notesCount: Int,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
)
