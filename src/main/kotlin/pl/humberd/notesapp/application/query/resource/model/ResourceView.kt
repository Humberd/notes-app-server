package pl.humberd.notesapp.application.query.resource.model

import pl.humberd.notesapp.application.common.list_view.DefaultViewList
import pl.humberd.notesapp.application.query.resource_revision.model.ResourceRevisionView
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.ResourceId
import java.sql.Timestamp

data class ResourceView(
    val id: ResourceId,
    val author: UserMinimalView,
    val latestRevision: ResourceRevisionView,
    val revisionsCount: Int,
    val createdAt: Timestamp
)

typealias ResourceViewList = DefaultViewList<ResourceView>

class ResourceViewListFilter()
