package pl.humberd.notesapp.tags

import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TagsService(
    private val repository: TagsRepository
) {
    @Transactional
    fun create(tag: Tag): Tag {
        return repository.save(tag)
    }

    fun createIfNotExists(name: String): Tag {
        val potentialTag = repository.findById(name)

        if (potentialTag.isPresent) {
            return potentialTag.get()
        }

        return create(Tag(name.toLowerCase(), name))
    }
}
