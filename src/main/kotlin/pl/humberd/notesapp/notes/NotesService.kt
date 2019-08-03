package pl.humberd.notesapp.notes

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.humberd.notesapp.ResourceNotFoundException
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
            createdAt = null,
            lastModifiedAt = null,
            title = body.title,
            content = body.content
        )
        generateNoteTags(body.tags, note)

        repository.save(note)
    }

    private fun generateNoteTags(tags: Array<String>, note: Note) {
        val newNoteTags = tags
            .distinctBy { it.toLowerCase() }
            .map { tagsService.createIfNotExists(it, note) }
            .toHashSet()

        note.tags = newNoteTags
    }

    @Transactional
    fun update(id: Long, body: CreateNoteDto) {
        val potentialNote = repository.findById(id)
        if (!potentialNote.isPresent) {
            throw ResourceNotFoundException()
        }

        potentialNote.get().also {
            it.title = body.title
            it.content = body.content

            generateNoteTags(body.tags, it)

            repository.save(it)
        }
    }

    fun readAll(): List<Note> {
        return repository.findAll()
    }

    fun readAll(tagIds: Collection<String>, title: String): List<Note> {
        val tagIdsLc = tagIds.map { it.toLowerCase() }
        if (tagIdsLc.size == 0) {
            return repository.findDistinctByTitleContainsIgnoreCase(title);
        }

        return repository.findDistinctByTitleContainsIgnoreCaseAndTagsIdIn(title, tagIdsLc)
    }

    fun readPage(pageable: Pageable): Page<Note> {
        return repository.findAll(pageable)
    }

    fun readPage(tagIds: Iterable<String>, pageable: Pageable): Page<Note> {
        val tagIdsLc = tagIds.map { it.toLowerCase() }

        return repository.findAllByTags_Id_In(tagIdsLc, pageable)
    }

}
