package pl.humberd.notesapp.application.query.group_post

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.list_view.ListViewExtra
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewList
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewListFilter
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.repository.GroupPostRepository
import kotlin.contracts.ExperimentalContracts

@Service
@ExperimentalContracts
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
            data = page.content.map { groupPostViewMapper.mapView(it, filter.userId) },
            extra = ListViewExtra.from(page)
        )
    }

    fun ASSERT_NOT_EXISTS(groupId: GroupId, resourceId: ResourceId) {
        val entityExists = groupPostRepository.existsByGroupIdAndResourceId(groupId, resourceId)

        ASSERT_NOT_EXIST(entityExists, "GroupPost already exists for Group{$groupId} and Resource{$resourceId}")
    }
}
