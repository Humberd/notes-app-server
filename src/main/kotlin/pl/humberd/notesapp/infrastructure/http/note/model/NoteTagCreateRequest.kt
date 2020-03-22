package pl.humberd.notesapp.infrastructure.http.note.model

import pl.humberd.notesapp.domain.entity.tag.model.TagId

class NoteTagCreateRequest {
    var tagId: TagId? = null
    var tagName: String? = null
}
