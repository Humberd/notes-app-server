package pl.humberd.notesapp.domain.entity.tag.model

import pl.humberd.notesapp.domain.entity.note.model.NoteId

interface TagWithNoteIdProjection {
    var tagInstance: Tag
    var noteId: NoteId
}
