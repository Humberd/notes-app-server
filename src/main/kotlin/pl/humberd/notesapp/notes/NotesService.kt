package pl.humberd.notesapp.notes

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.humberd.notesapp.tags.TagsService
import javax.transaction.Transactional

@Service
class NotesService(
    private val repository: NotesRepository,
    private val tagsService: TagsService
) {
    @Transactional
    fun create(body: CreateNoteDto) {
        val note = Note(
            id = 0,
            title = body.title,
            content = body.content
        )

        body.tags.forEach { rawTag ->
            val tag = tagsService.createIfNotExists(rawTag, note)
            (note.tags as HashSet).add(tag)
        }

        repository.save(note)

    }

    fun readAll(): List<Note> {
        return repository.findAll()
    }

    fun readPage(pageable: Pageable): Page<Note> {
        return repository.findAll(pageable)
    }
}
