package pl.humberd.notesapp.domain.entity.tag.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.model.TagId
import pl.humberd.notesapp.domain.entity.tag.model.TagWithNoteIdProjection
import pl.humberd.notesapp.domain.entity.user.model.UserId
import java.util.*

@Repository
interface TagRepository : RefreshableJpaRepository<Tag, TagId> {
    fun existsByUserIdAndNameLc(userId: UserId, nameLc: String): Boolean

    fun findByUserIdAndNameLc(userId: UserId, nameLc: String): Optional<Tag>

    @Query("select tag from Tag tag inner join NoteTag nt on tag.id = nt.id.tagId where nt.id.noteId = :noteId")
    fun findAllByNote(
        @Param("noteId") noteId: String,
        pageable: Pageable
    ): Page<Tag>

    @Query("select tag as tagInstance, nt.id.noteId as noteId from Tag tag inner join NoteTag nt on tag.id = nt.id.tagId where nt.id.noteId in (:noteIds)")
    fun PROJECT_findAllByNotes(
        @Param("noteIds") noteIds: Collection<String>
    ): List<TagWithNoteIdProjection>

    fun findAllByUserId(
        userId: UserId,
        pageable: Pageable
    ): Page<Tag>

}
