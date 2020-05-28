package pl.humberd.notesapp.application.query.resource.model

import pl.humberd.notesapp.application.common.list_view.DefaultViewList
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceRevisionId
import java.sql.Timestamp

data class ResourceView(
    val id: ResourceId,
    val author: UserMinimalView,
    val latestRevisionId: ResourceRevisionId,
    val revisionsCount: Int,
    val createdAt: Timestamp
)

typealias ResourceViewList = DefaultViewList<ResourceView>

class ResourceViewListFilter()
