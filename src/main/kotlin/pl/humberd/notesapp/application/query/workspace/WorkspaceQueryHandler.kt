package pl.humberd.notesapp.application.query.workspace

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceView
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceViewList
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceViewListFilter
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId
import pl.humberd.notesapp.domain.entity.workspace.repository.WorkspaceRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class WorkspaceQueryHandler(
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceViewMapper: WorkspaceViewMapper
) {
    fun listView(filter: WorkspaceViewListFilter.ByUser): WorkspaceViewList {
        val page = workspaceRepository.findAllByUserId(filter.userId, filter.pageable)

        return WorkspaceViewList(
            data = page.content.map(workspaceViewMapper::mapView),
            extra = ListViewExtra.from(page)
        )
    }

    fun view(id: WorkspaceId): WorkspaceView {
        val workspace = workspaceRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(workspace, id)

        return workspaceViewMapper.mapView(workspace)
    }


}
