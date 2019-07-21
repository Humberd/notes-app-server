package pl.humberd.notesapp.notes

import org.springframework.stereotype.Service
import pl.humberd.notesapp.tags.Tag
import pl.humberd.notesapp.tags.TagsService
import javax.transaction.Transactional

@Service
class NotesService(
    private val repository: NotesRepository,
    private val tagsService: TagsService
) {
    @Transactional
    fun create(body: CreateNoteDto) {
        val tags = body.tags.map {
            tagsService.createIfNotExists(it)
        }

        repository.save(
            Note(
                id = 0,
                title = body.title,
                content = body.content,
                tags = tags.toSet()
            )
        )
    }
}
