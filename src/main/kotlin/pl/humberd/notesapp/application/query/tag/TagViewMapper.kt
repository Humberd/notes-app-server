package pl.humberd.notesapp.application.query.tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.domain.entity.tag.model.Tag

@Service
class TagViewMapper {
    fun mapView(tag: Tag) = TagView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor,
        notesCount = tag.notesCount,
        createdAt = tag.metadata.createdAt,
        updatedAt = tag.metadata.updatedAt
    )

    fun mapMinimalView(tag: Tag) = TagMinimalView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor
    )
}
