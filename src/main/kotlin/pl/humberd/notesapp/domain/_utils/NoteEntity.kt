package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "note", schema = "public", catalog = "admin")
open class NoteEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "author_id", nullable = true, insertable = false, updatable = false)
    var authorId: String? = null

    @get:Basic
    @get:Column(name = "url", nullable = false)
    var url: String? = null

    @get:Basic
    @get:Column(name = "title", nullable = false)
    var title: String? = null

    @get:Basic
    @get:Column(name = "content", nullable = false)
    var content: String? = null

    @get:Basic
    @get:Column(name = "is_starred", nullable = false)
    var isStarred: java.lang.Boolean? = null

    @get:Basic
    @get:Column(name = "is_deleted", nullable = false)
    var isDeleted: java.lang.Boolean? = null

    @get:Basic
    @get:Column(name = "comments_count", nullable = false)
    var commentsCount: Int? = null

    @get:Basic
    @get:Column(name = "votes_score", nullable = false)
    var votesScore: Int? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "author_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:OneToMany(mappedBy = "refNoteEntity")
    var refNoteCommentEntities: List<NoteCommentEntity>? = null

    @get:OneToMany(mappedBy = "refNoteEntity")
    var refNoteUserVoteEntities: List<NoteUserVoteEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "authorId = $authorId " +
                "url = $url " +
                "title = $title " +
                "content = $content " +
                "isStarred = $isStarred " +
                "isDeleted = $isDeleted " +
                "commentsCount = $commentsCount " +
                "votesScore = $votesScore " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as NoteEntity

        if (id != other.id) return false
        if (authorId != other.authorId) return false
        if (url != other.url) return false
        if (title != other.title) return false
        if (content != other.content) return false
        if (isStarred != other.isStarred) return false
        if (isDeleted != other.isDeleted) return false
        if (commentsCount != other.commentsCount) return false
        if (votesScore != other.votesScore) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

