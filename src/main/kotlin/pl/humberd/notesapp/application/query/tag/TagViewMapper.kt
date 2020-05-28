package pl.humberd.notesapp.application.query.tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.GroupViewMapper
import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.domain.entity.TagEntity
import pl.humberd.notesapp.domain.repository.GroupRepository

@Service
class TagViewMapper(
    private val groupRepository: GroupRepository,
    private val groupViewMapper: GroupViewMapper
) {
    fun mapView(tag: TagEntity) = TagView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor,
        notesCount = tag.notesCount,
        publishToGroups = groupRepository.findAllByTagTrigger(tag.id).map(groupViewMapper::mapMinimalView),
        createdAt = tag.metadata.createdAt,
        updatedAt = tag.metadata.updatedAt
    )

    fun mapMinimalView(tag: TagEntity) = TagMinimalView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor
    )
}
