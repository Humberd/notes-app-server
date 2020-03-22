package pl.humberd.notesapp.domain.entity.note_tag.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.tag.model.TagId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "note_tag", schema = "public")
class NoteTag(
    @EmbeddedId
    val id: NoteTagId
) {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "NoteTag(id=$id)"
    }
}

@Embeddable
data class NoteTagId(
    @Column(name = "note_id")
    val noteId: NoteId,

    @Column(name = "tag_id")
    val tagId: TagId
) : Serializable
