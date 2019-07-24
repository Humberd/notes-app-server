package pl.humberd.notesapp.notes

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotesRepository : JpaRepository<Note, Long> {

    fun findByTitleContainsIgnoreCaseAndTagsIdIn(title: String, tagIds: List<String>): List<Note>
    fun findByTitleContainsIgnoreCase(title: String): List<Note>
    fun findAllByTags_Id_In(tagIds: List<String>, pageable: Pageable): Page<Note>
}
