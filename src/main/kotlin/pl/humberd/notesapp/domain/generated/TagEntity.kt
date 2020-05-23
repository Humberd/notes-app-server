package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "tag", schema = "public", catalog = "admin")
open class TagEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "name_lc", nullable = false)
    var nameLc: String? = null

    @get:Basic
    @get:Column(name = "background_color", nullable = true)
    var backgroundColor: String? = null

    @get:Basic
    @get:Column(name = "notes_count", nullable = false)
    var notesCount: Int? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:OneToMany(mappedBy = "refTagEntity")
    var refResourceTagEntities: List<ResourceTagEntity>? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:OneToMany(mappedBy = "refTagEntity")
    var refUserGroupMembershipTagTriggerEntities: List<UserGroupMembershipTagTriggerEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "userId = $userId " +
                "name = $name " +
                "nameLc = $nameLc " +
                "backgroundColor = $backgroundColor " +
                "notesCount = $notesCount " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as TagEntity

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (name != other.name) return false
        if (nameLc != other.nameLc) return false
        if (backgroundColor != other.backgroundColor) return false
        if (notesCount != other.notesCount) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

