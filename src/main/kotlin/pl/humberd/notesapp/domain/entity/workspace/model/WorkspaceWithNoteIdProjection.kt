package pl.humberd.notesapp.domain.entity.workspace.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId

interface WorkspaceWithNoteIdProjection {
    var workspaceInstance: Workspace
    var noteId: NoteId
}
