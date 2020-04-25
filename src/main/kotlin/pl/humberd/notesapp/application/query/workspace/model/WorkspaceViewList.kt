package pl.humberd.notesapp.application.query.workspace.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.application.query.DefaultViewList
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.user.model.UserId

typealias WorkspaceViewList = DefaultViewList<WorkspaceView>
typealias WorkspaceMinimalViewList = DefaultViewList<WorkspaceMinimalView>

sealed class WorkspaceListFilter(
    val pageable: Pageable
) {

    class ByUser(
        pageable: Pageable,
        val userId: UserId
    ) : WorkspaceListFilter(pageable)

    class ByNote(
        pageable: Pageable,
        val noteId: NoteId
    ) : WorkspaceListFilter(pageable)
}
