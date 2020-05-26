package pl.humberd.notesapp.application.query.tag

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.common.list_view.ListViewExtra
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.application.query.tag.model.TagViewList
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.repository.TagRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class TagQueryHandler(
    private val tagRepository: TagRepository,
    private val tagViewMapper: TagViewMapper
) {

    fun listView(filter: TagListFilter.ByUser): TagViewList {
        val page = tagRepository.findAllByUserId(filter.userId, filter.pageable)

        return TagViewList(
            data = page.content.map(tagViewMapper::mapView),
            extra = ListViewExtra.from(page)
        )
    }

    fun view(id: TagId): TagView {
        val tag = tagRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(tag, id)

        return tagViewMapper.mapView(tag)
    }
}
