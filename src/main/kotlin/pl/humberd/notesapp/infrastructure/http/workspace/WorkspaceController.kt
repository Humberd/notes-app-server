package pl.humberd.notesapp.infrastructure.http.workspace

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.workspace.WorkspaceCommandHandler
import pl.humberd.notesapp.application.command.workspace.model.WorkspaceCreateCommand
import pl.humberd.notesapp.application.command.workspace.model.WorkspaceDeleteCommand
import pl.humberd.notesapp.application.command.workspace.model.WorkspaceIsUsersCommand
import pl.humberd.notesapp.application.command.workspace.model.WorkspacePatchCommand
import pl.humberd.notesapp.application.query.workspace.WorkspaceQueryHandler
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceView
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceViewList
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceViewListFilter
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.workspace.model.WorkspaceCreateRequest
import pl.humberd.notesapp.infrastructure.http.workspace.model.WorkspacePatchRequest
import java.security.Principal
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/workspaces")
@RestController
class WorkspaceController(
    private val workspaceQueryHandler: WorkspaceQueryHandler,
    private val workspaceCommandHandler: WorkspaceCommandHandler
) {

    @GetMapping
    fun readList(
        pageable: Pageable,
        principal: Principal
    ): ResponseEntity<WorkspaceViewList> {
        val viewList = workspaceQueryHandler.listView(
            WorkspaceViewListFilter.ByUser(
                pageable = pageable,
                userId = principal.name
            )
        )

        return ResponseBuilder.ok(viewList)
    }

    @PostMapping
    fun create(
        @Valid @RequestBody body: WorkspaceCreateRequest,
        principal: Principal
    ): ResponseEntity<WorkspaceView> {
        val workspace = workspaceCommandHandler.createAndRefresh(
            WorkspaceCreateCommand(
                name = body.name,
                userId = principal.name
            )
        )

        val workspaceView = workspaceQueryHandler.view(workspace.id)

        return ResponseBuilder.created(workspaceView)
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable("id") id: WorkspaceId,
        @RequestBody body: WorkspacePatchRequest,
        principal: Principal
    ): ResponseEntity<WorkspaceView> {
        workspaceCommandHandler.ensureIsAuthor(
            WorkspaceIsUsersCommand(
                workspaceId = id,
                userId = principal.name
            )
        )

        workspaceCommandHandler.patchAndRefresh(
            WorkspacePatchCommand(
                id = id,
                name = body.name,
                userId = principal.name
            )
        )

        val workspaceView = workspaceQueryHandler.view(id)

        return ResponseBuilder.updated(workspaceView)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: WorkspaceId,
        principal: Principal
    ): ResponseEntity<Unit> {
        workspaceCommandHandler.ensureIsAuthor(
            WorkspaceIsUsersCommand(
                workspaceId = id,
                userId = principal.name
            )
        )

        workspaceCommandHandler.delete(
            WorkspaceDeleteCommand(
                id = id
            )
        )

        return ResponseBuilder.noContent()
    }


}
