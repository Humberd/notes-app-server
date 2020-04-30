package pl.humberd.notesapp.application.query.note

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.note.model.NoteView
import pl.humberd.notesapp.application.query.tag.TagQueryHandler
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.workspace.WorkspaceQueryHandler
import pl.humberd.notesapp.application.query.workspace.model.WorkspaceListFilter
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import pl.humberd.notesapp.domain.entity.workspace.repository.WorkspaceRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class NoteQueryHandler(
    private val noteRepository: NoteRepository,
    private val tagRepository: TagRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val userQueryHandler: UserQueryHandler,
    private val tagQueryHandler: TagQueryHandler,
    private val workspaceQueryHandler: WorkspaceQueryHandler,
    private val noteViewMapper: NoteViewMapper
) {

    fun listView(filter: NoteListFilter): NoteListView {
        val list = when (filter) {
            is NoteListFilter.Compound -> findBy(filter)
        }

        return NoteListView(
            data = noteViewMapper.mapViewList(list),
            extra = ListViewExtra.from(PageImpl(list))
        )
    }

    fun view(id: NoteId): NoteView {
        val note = noteRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(note, id)

        return noteViewMapper.mapView(
            note = note,
            author = userQueryHandler.minimalView(note.authorId),
            tags = tagQueryHandler.listMinimalView(
                TagListFilter.ByNote(
                    noteId = id,
                    pageable = Pageable.unpaged()
                )
            ).data,
            workspaces = workspaceQueryHandler.listMinimalView(
                WorkspaceListFilter.ByNote(
                    noteId = id,
                    pageable = Pageable.unpaged()
                )
            ).data
        )
    }

    private fun findBy(command: NoteListFilter.Compound): List<Note> {
        var notes = when {
            command.authorId.isNullOrBlank() -> this.noteRepository.findAll(command.pageable)
            else -> this.noteRepository.findAllByAuthorId(command.authorId, command.pageable)
        }.content

        if (!command.url.isNullOrBlank()) {
            notes = notes.filter { it.urlLc == command.url.toLowerCase() }
        }

        if (!command.workspaceId.isNullOrBlank()) {
            val noteIds = notes.map { it.id }
            val workspaces =
                workspaceRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.workspaceInstance })

            notes = notes.filter { note ->
                val noteWorkspaces = workspaces[note.id]
                if (noteWorkspaces == null) {
                    return@filter false
                }

                noteWorkspaces.any { workspace -> workspace.id == command.workspaceId }
            }
        }

        if (!command.tagsIds.isNullOrEmpty()) {
            val noteIds = notes.map { it.id }
            val tags = tagRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.tagInstance })

            notes = notes.filter { note ->
                command.tagsIds.all { commandTagId ->
                    tags[note.id]?.any { tag -> tag.id == commandTagId } ?: false
                }
            }
        }

        if (!command.query.isNullOrBlank()) {
            val queryLc = command.query.trim().toLowerCase()
            val noteIds = notes.map { it.id }
            val tags = tagRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.tagInstance })


            notes = notes.filter {
                it.title.contains(queryLc)
                        || it.urlLc?.contains(queryLc) ?: false
                        || it.content?.contains(queryLc) ?: false
                        || tags[it.id]?.find { tag -> tag.nameLc.contains(queryLc) } !== null
            }
        }

        return notes
    }
}
