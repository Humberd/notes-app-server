package pl.humberd.notesapp.domain.entity.note.repository

import org.intellij.lang.annotations.Language
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId

@Repository
interface NoteRepository : RefreshableJpaRepository<Note, NoteId> {

    fun findAllByAuthorId(
        authorId: String,
        pageable: Pageable
    ): Page<Note>


    @Suppress("JpaQlInspection")
    @Language("PostgreSQL")
    @Query(
        value = "select * from Note note where note.author_id = :authorId and note.search_vector @@ plainto_tsquery(:webSearchQuery)",
        countQuery = "select count(*) from Note",
        nativeQuery = true
    )
    fun findAllByWebSearchQuery(
        @Param("authorId") authorId: String,
        @Param("webSearchQuery") webSearchQuery: String,
        pageable: Pageable
    ): Page<Note>
}
