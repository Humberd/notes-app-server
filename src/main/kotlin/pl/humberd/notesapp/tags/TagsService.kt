package pl.humberd.notesapp.tags

import org.springframework.stereotype.Service
import pl.humberd.notesapp.notes.Note
import javax.transaction.Transactional

@Service
class TagsService(
    private val repository: TagsRepository
) {
    @Transactional
    fun create(tag: Tag): Tag {
        return repository.save(tag)
    }

    fun createIfNotExists(tagName: String, note: Note): Tag {
        val tagNameLc = tagName.toLowerCase()
        val potentialTag = repository.findById(tagNameLc)

        if (potentialTag.isPresent) {
            return potentialTag.get()
        }

        return create(Tag(tagNameLc, tagName, hashSetOf(note)))
    }

    fun readAll(): List<Tag> {
        return repository.findAll();
    }
}
