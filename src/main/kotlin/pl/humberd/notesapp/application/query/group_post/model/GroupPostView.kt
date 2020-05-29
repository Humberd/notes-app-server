package pl.humberd.notesapp.application.query.group_post.model

import pl.humberd.notesapp.application.common.list_view.DefaultViewList
import pl.humberd.notesapp.application.query.group.model.GroupMinimalView
import pl.humberd.notesapp.application.query.resource.model.ResourceView
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.GroupPostId
import pl.humberd.notesapp.domain.entity.UserId

data class GroupPostView(
    val id: GroupPostId,
    val group: GroupMinimalView,
    val resource: ResourceView
)

typealias GroupPostViewList = DefaultViewList<GroupPostView>

data class GroupPostViewListFilter(
    val groupId: GroupId,
    val userId: UserId
)
