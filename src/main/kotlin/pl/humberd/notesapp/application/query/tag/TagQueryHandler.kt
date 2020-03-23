package pl.humberd.notesapp.application.query.tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.tag.model.*
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository

@Service
class TagQueryHandler(
    private val tagRepository: TagRepository
) {

    fun listView(filter: TagListFilter.ByUser): TagViewList {
        val page = tagRepository.findAllByUserId(filter.userId, filter.pageable)

        return TagViewList(
            data = mapViewList(page.content),
            extra = ListViewExtra.from(page)
        )
    }

    fun mapViewList(tags: List<Tag>) = tags.map(this::mapView)

    fun mapView(tag: Tag) = TagView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor,
        notesCount = tag.notesCount,
        createdAt = tag.metadata.createdAt,
        updatedAt = tag.metadata.updatedAt
    )

    fun listMinimalView(filter: TagListFilter.ByNote): TagMinimalViewList {
        val page = tagRepository.findAllByNote(filter.noteId, filter.pageable)

        return TagMinimalViewList(
            data = mapMinimalViewList(page.content),
            extra = ListViewExtra.from(page)
        )
    }

    fun mapMinimalViewList(tags: List<Tag>) = tags.map(this::mapMinimalView)

    fun mapMinimalView(tag: Tag) = TagMinimalView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor
    )
}
