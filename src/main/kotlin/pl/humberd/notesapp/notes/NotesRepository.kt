package pl.humberd.notesapp.notes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface NotesRepository : JpaRepository<Note, Long> {

    fun findAllByTags_Id_In(tagIds: List<String>): List<Note>
}
