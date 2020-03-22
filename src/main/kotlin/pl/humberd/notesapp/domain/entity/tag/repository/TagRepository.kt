package pl.humberd.notesapp.domain.entity.tag.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.model.TagId

@Repository
interface TagRepository : RefreshableJpaRepository<Tag, TagId> {
    fun existsByNameLc(nameLc: String): Boolean
}
