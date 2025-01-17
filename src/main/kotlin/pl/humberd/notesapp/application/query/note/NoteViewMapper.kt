package pl.humberd.notesapp.application.query.note

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.query.note.model.NoteView
import pl.humberd.notesapp.application.query.tag.TagViewMapper
import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.application.query.workspace.WorkspaceViewMapper
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceMinimalView
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import pl.humberd.notesapp.domain.entity.workspace.repository.WorkspaceRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class NoteViewMapper(
    private val userQueryHandler: UserQueryHandler,
    private val tagRepository: TagRepository,
    private val tagViewMapper: TagViewMapper,
    private val workspaceRepository: WorkspaceRepository,
    private val workspaceViewMapper: WorkspaceViewMapper
) {
    fun mapViewList(notes: List<Note>): List<NoteView> {
        val authorIds = notes.map { it.authorId }
        val authors = userQueryHandler.minimalViewDictionary(authorIds)

        val noteIds = notes.map { it.id }
        val tags =
            tagRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.tagInstance })
        val workspaces =
            workspaceRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.workspaceInstance })


        return notes.map {
            val author = authors.get(it.authorId)
            ASSERT_NOT_NULL(author, it.authorId)

            val noteTags = tags.getOrElse(it.id) { emptyList() }.sortedBy { it.name }
            val noteWorkspaces = workspaces.getOrElse(it.id) { emptyList() }.sortedBy { it.name }

            return@map mapView(
                note = it,
                author = author,
                tags = noteTags.map(tagViewMapper::mapMinimalView),
                workspaces = noteWorkspaces.map(workspaceViewMapper::mapMinimalView)
            )
        }
    }

    fun mapView(
        note: Note,
        author: UserMinimalView,
        tags: List<TagMinimalView>,
        workspaces: List<WorkspaceMinimalView>
    ) = NoteView(
        id = note.id,
        author = author,
        url = note.url,
        title = note.title,
        content = note.content,
        tags = tags,
        workspaces = workspaces,
        createdAt = note.metadata.createdAt,
        updatedAt = note.metadata.updatedAt
    )
}
