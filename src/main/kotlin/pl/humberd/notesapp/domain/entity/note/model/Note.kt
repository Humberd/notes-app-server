package pl.humberd.notesapp.domain.entity.note.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.user.model.UserId
import javax.persistence.*

typealias NoteId = String

@Entity
@Table(name = "note", schema = "public")
class Note(
    @Id
    @Column(name = "id")
    val id: NoteId,

    @Column(name = "author_id")
    val authorId: UserId,

    @Column(name = "url")
    var url: String?,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String?
) {

    @Column(name = "url_lc", updatable = false, insertable = false)
    var urlLc: String? = null
        private set

    @Column(name = "comments_count", updatable = false, insertable = false)
    var commentsCount: Int = 0
        private set

    @Column(name = "votes_score", updatable = false, insertable = false)
    var votesScore: Int = 0
        private set

    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "Note(id='$id', authorId='$authorId', url='$url', title='$title', content='$content', commentsCount=$commentsCount, votesScore=$votesScore)"
    }

}
