package pl.humberd.notesapp.application.command.note_workspace

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note_workspace.model.NoteWorkspaceCreateCommand
import pl.humberd.notesapp.application.command.note_workspace.model.NoteWorkspaceDeleteCommand
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.note_workspace.model.NoteWorkspace
import pl.humberd.notesapp.domain.entity.note_workspace.model.NoteWorkspaceId
import pl.humberd.notesapp.domain.entity.note_workspace.repository.NoteWorkspaceRepository
import pl.humberd.notesapp.domain.entity.workspace.repository.WorkspaceRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class NoteWorkspaceCommandHandler(
    private val noteWorkspaceRepository: NoteWorkspaceRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val noteRepository: NoteRepository
) {

    fun create(command: NoteWorkspaceCreateCommand): NoteWorkspace {
        val workspace = workspaceRepository.findByIdOrNull(command.workspaceId)
        ASSERT_NOT_NULL(workspace, command.workspaceId)

        val note = noteRepository.findByIdOrNull(command.noteId)
        ASSERT_NOT_NULL(note, command.noteId)

        val noteWorkspaceId = NoteWorkspaceId(
            noteId = command.noteId,
            workspaceId = command.workspaceId
        )

        val noteWorkspaceExists = noteWorkspaceRepository.existsById(noteWorkspaceId)
        ASSERT_NOT_EXIST<NoteWorkspace>(noteWorkspaceExists, noteWorkspaceId.toString())

        return noteWorkspaceRepository.save(
            NoteWorkspace(
                id = noteWorkspaceId
            )
        )
    }

    fun createAndRefresh(command: NoteWorkspaceCreateCommand): NoteWorkspace {
        return create(command).also {
            noteWorkspaceRepository.saveFlushRefresh(it)
        }
    }

    fun delete(command: NoteWorkspaceDeleteCommand) {
        val noteWorkspaceId = NoteWorkspaceId(
            noteId = command.noteId,
            workspaceId = command.workspaceId
        )

        val noteWorkspaceExists = noteWorkspaceRepository.existsById(noteWorkspaceId)
        ASSERT_NOT_EXIST<NoteWorkspace>(noteWorkspaceExists, noteWorkspaceId.toString())

        noteWorkspaceRepository.deleteById(noteWorkspaceId)
    }
}
