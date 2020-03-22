package pl.humberd.notesapp.application.query.tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.tag.model.NoteTagListView
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository

@Service
class TagQueryHandler(
    private val tagRepository: TagRepository
) {
    fun listView(filter: TagListFilter): NoteTagListView {
        val page = tagRepository.findAllByNote(filter.noteId, filter.pageable)

        return NoteTagListView(
            data = mapViewList(page.content),
            extra = ListViewExtra.from(page)
        )
    }

    private fun mapViewList(tags: List<Tag>) = tags.map(this::mapView)

    private fun mapView(tag: Tag) = TagView(
        id = tag.id,
        name = tag.name,
        backgroundColor = tag.backgroundColor
    )
}
