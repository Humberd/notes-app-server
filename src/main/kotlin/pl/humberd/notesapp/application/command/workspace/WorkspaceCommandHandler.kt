package pl.humberd.notesapp.application.command.workspace

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.workspace.model.WorkspaceCreateCommand
import pl.humberd.notesapp.application.command.workspace.model.WorkspaceIsUsersCommand
import pl.humberd.notesapp.application.command.workspace.model.WorkspacePatchCommand
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.workspace.model.Workspace
import pl.humberd.notesapp.domain.entity.workspace.repository.WorkspaceRepository
import javax.transaction.Transactional
import javax.validation.ValidationException
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class WorkspaceCommandHandler(
    private val workspaceRepository: WorkspaceRepository
) {
    fun create(command: WorkspaceCreateCommand): Workspace {
        VALIDATE_NAME(command.name)

        val workspaceExists = workspaceRepository.existsByUserIdAndNameLc(
            userId = command.userId,
            nameLc = command.name.toLowerCase()
        )
        ASSERT_NOT_EXIST<Workspace>(workspaceExists, command.name)

        return this.workspaceRepository.save(
            Workspace(
                id = IdGenerator.random(Workspace::class),
                userId = command.userId,
                name = command.userId
            )
        )
    }

    fun createAndRefresh(command: WorkspaceCreateCommand): Workspace {
        return create(command).also {
            workspaceRepository.saveFlushRefresh(it)
        }
    }

    fun patch(command: WorkspacePatchCommand): Workspace {
        val workspace = workspaceRepository.findByIdOrNull(command.id)
        ASSERT_NOT_NULL(workspace, command.id)

        if (command.name !== null) {
            VALIDATE_NAME(command.name)
        }

        workspace.also {
            it.name = command.name ?: it.name
        }

        return workspaceRepository.save(workspace)
    }

    fun patchAndRefresh(command: WorkspacePatchCommand): Workspace {
        return patch(command).also {
            workspaceRepository.saveFlushRefresh(it)
        }
    }

    private fun VALIDATE_NAME(name: String) {
        if (name.isBlank()) {
            throw ValidationException("Workspace Name cannot be blank")
        }
    }

    fun ensureIsAuthor(command: WorkspaceIsUsersCommand) {
        val workspace = workspaceRepository.findByIdOrNull(command.workspaceId)
        ASSERT_NOT_NULL(workspace, command.workspaceId)
        if (workspace.userId != command.userId) {
            throw ForbiddenException(Workspace::class, command.workspaceId)
        }
    }
}
