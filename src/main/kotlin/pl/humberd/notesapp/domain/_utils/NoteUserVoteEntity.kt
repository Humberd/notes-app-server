package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "note_user_vote", schema = "public", catalog = "admin")
open class NoteUserVoteEntity {
    @get:Id
    @get:Column(name = "id", nullable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "note_id", nullable = false, insertable = false, updatable = false)
    var noteId: String? = null

    @get:Basic
    @get:Column(name = "is_upvote", nullable = false)
    var isUpvote: java.lang.Boolean? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "note_id", referencedColumnName = "id")
    var refNoteEntity: NoteEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "userId = $userId " +
                "noteId = $noteId " +
                "isUpvote = $isUpvote " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoteUserVoteEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (noteId != other.noteId) return false
        if (isUpvote != other.isUpvote) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

