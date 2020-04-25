package pl.humberd.notesapp.application.command.note_workspace.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId

data class NoteWorkspaceDeleteCommand(
    val noteId: NoteId,
    val workspaceId: WorkspaceId
)
