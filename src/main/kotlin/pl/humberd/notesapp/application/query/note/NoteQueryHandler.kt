package pl.humberd.notesapp.application.query.note

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
import pl.humberd.notesapp.application.query.tag.model.TagMinimalView
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class NoteQueryHandler(
    private val noteRepository: NoteRepository,
    private val userQueryHandler: UserQueryHandler,
    private val tagQueryHandler: TagQueryHandler,
    private val tagRepository: TagRepository
) {

    fun listView(filter: NoteListFilter): NoteListView {
        val page = when (filter) {
            is NoteListFilter.Regular -> noteRepository.findAllByAuthorId(filter.authorId, filter.pageable)
            is NoteListFilter.ByQuery -> noteRepository.findAllByWebSearchQuery(filter.authorId, filter.query, filter.pageable)
        }


        return NoteListView(
            data = mapViewList(page.content),
            extra = ListViewExtra.from(page)
        )
    }

    fun view(id: NoteId): NoteView {
        val note = noteRepository.findByIdOrNull(id)
        ASSERT_NOT_NULL(note, id)

        return mapView(
            note = note,
            author = userQueryHandler.minimalView(note.authorId),
            tags = tagQueryHandler.listMinimalView(
                TagListFilter.ByNote(
                    noteId = id,
                    pageable = Pageable.unpaged()
                )
            ).data
        )
    }

    private fun mapViewList(notes: List<Note>): List<NoteView> {
        val authorIds = notes.map { it.authorId }
        val authors = userQueryHandler.minimalViewDictionary(authorIds)

        val noteIds = notes.map { it.id }
        val tags =
            tagRepository.PROJECT_findAllByNotes(noteIds).groupBy({ it.noteId }, { it.tagInstance })

        return notes.map {
            val author = authors.get(it.authorId)
            ASSERT_NOT_NULL(author, it.authorId)

            val noteTags = tags.getOrElse(it.id){ emptyList()}.sortedBy { it.name }

            return@map mapView(
                note = it,
                author = author,
                tags = tagQueryHandler.mapMinimalViewList(noteTags)
            )
        }
    }

    private fun mapView(
        note: Note,
        author: UserMinimalView,
        tags: List<TagMinimalView>
    ) = NoteView(
        id = note.id,
        author = author,
        url = note.url,
        title = note.title,
        content = note.content,
        tags = tags,
        createAt = note.metadata.createdAt,
        updatedAt = note.metadata.updatedAt
    )
}
