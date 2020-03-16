package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "note_comment", schema = "public", catalog = "admin")
open class NoteCommentEntity {
    @get:Id
    @get:Column(name = "id", nullable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "author_id", nullable = true, insertable = false, updatable = false)
    var authorId: String? = null

    @get:Basic
    @get:Column(name = "note_id", nullable = false, insertable = false, updatable = false)
    var noteId: String? = null

    @get:Basic
    @get:Column(name = "content", nullable = false)
    var content: String? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "author_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "note_id", referencedColumnName = "id")
    var refNoteEntity: NoteEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "authorId = $authorId " +
                "noteId = $noteId " +
                "content = $content " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoteCommentEntity

        if (id != other.id) return false
        if (authorId != other.authorId) return false
        if (noteId != other.noteId) return false
        if (content != other.content) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

