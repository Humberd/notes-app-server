package pl.humberd.notesapp.application.query.tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.tag.model.NoteTagMinimalListView
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository

@Service
class TagQueryHandler(
    private val tagRepository: TagRepository
) {
    fun listView(filter: TagListFilter): NoteTagMinimalListView {
        val page = tagRepository.findAllByNote(filter.noteId, filter.pageable)

        return NoteTagMinimalListView(
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
