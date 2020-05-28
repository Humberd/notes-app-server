package pl.humberd.notesapp.application.query.group_post

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.list_view.ListViewExtra
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewList
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewListFilter
import pl.humberd.notesapp.domain.repository.GroupPostRepository

@Service
class GroupPostQueryHandler(
    private val groupPostRepository: GroupPostRepository,
    private val groupPostViewMapper: GroupPostViewMapper
) {

    fun viewList(filter: GroupPostViewListFilter): GroupPostViewList {
        val page = groupPostRepository.findAllByGroupId(
            groupId = filter.groupId,
            pageable = Pageable.unpaged()
        )

        return GroupPostViewList(
            data = page.content.map(groupPostViewMapper::mapView),
            extra = ListViewExtra.from(page)
        )
    }
}
