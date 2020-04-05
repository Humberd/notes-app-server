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
        value = """
        select distinct on(note)*
        from Note note
             left join note_tag nt on note.id = nt.note_id
             join tag t on nt.tag_id = t.id
where note.author_id = :authorId
  and (
        t.name_lc like '%' || :webSearchQueryLc || '%'
        or note.url ilike '%' || :webSearchQueryLc || '%'
        or note.title ilike '%' || :webSearchQueryLc || '%'
        or note.content ilike '%' || :webSearchQueryLc || '%'
    )
            """,
        countQuery = "select count(*) from Note",
        nativeQuery = true

    )
    fun findAllByWebSearchQuery(
        @Param("authorId") authorId: String,
        @Param("webSearchQueryLc") webSearchQueryLc: String,
        pageable: Pageable
    ): Page<Note>
}
