package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "user", schema = "public", catalog = "admin")
open class UserEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "name_lc", nullable = false)
    var nameLc: String? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refNoteEntities: List<NoteEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refNoteCommentEntities: List<NoteCommentEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refNoteUserVoteEntities: List<NoteUserVoteEntity>? = null

    @get:OneToOne(mappedBy = "refUserEntity")
    var refUserAuthEntity: UserAuthEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "nameLc = $nameLc " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (nameLc != other.nameLc) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

