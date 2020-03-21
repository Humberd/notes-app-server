package pl.humberd.notesapp.application.query.note

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.NOT_NULL
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.note.model.NoteListFilter
import pl.humberd.notesapp.application.query.note.model.NoteListView
import pl.humberd.notesapp.application.query.note.model.NoteView
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserMinimalView
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Service
class NoteQueryHandler(
    private val noteRepository: NoteRepository,
    private val userQueryHandler: UserQueryHandler
) {

    fun listView(filter: NoteListFilter): NoteListView {
        val page = noteRepository.findAll(filter.pageable)

        return NoteListView(
            data = mapViewList(page.content),
            extra = ListViewExtra.from(page)
        )
    }

    fun view(id: NoteId): NoteView {
        val note = noteRepository.findByIdOrNull(id)
        NOT_NULL(note, id)

        return mapView(note, userQueryHandler.minimalView(note.authorId))
    }

    private fun mapViewList(notes: List<Note>): List<NoteView> {
        val authorIds = notes.map { it.authorId }
        val authors = userQueryHandler.minimalViewDictionary(authorIds)

        return notes.map {
            val author = authors.get(it.authorId)
            NOT_NULL(author, it.authorId)

            return@map mapView(it, author)
        }
    }

    private fun mapView(
        note: Note,
        author: UserMinimalView
    ): NoteView {
        return NoteView(
            id = note.id,
            author = author,
            url = note.url,
            title = note.title,
            content = note.content,
            createAt = note.metadata.createdAt,
            updatedAt = note.metadata.updatedAt
        )
    }
}
