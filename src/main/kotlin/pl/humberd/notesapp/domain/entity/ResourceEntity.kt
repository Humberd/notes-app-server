package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "resource", schema = "public", catalog = "admin")
open class ResourceEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "author_id", nullable = true, insertable = false, updatable = false)
    var authorId: String? = null

    @get:Basic
    @get:Column(name = "revisions_count", nullable = false)
    var revisionsCount: Int? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "latest_revision_id", nullable = true, insertable = false, updatable = false)
    var latestRevisionId: String? = null

    @get:OneToMany(mappedBy = "refResourceEntity")
    var refGroupPostEntities: List<GroupPostEntity>? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "author_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    @get:ManyToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "latest_revision_id", referencedColumnName = "id")
    var refResourceRevisionEntity: ResourceRevisionEntity? = null

    @get:OneToMany(mappedBy = "refResourceEntity")
    var refResourceRevisionEntities: List<ResourceRevisionEntity>? = null

    @get:OneToMany(mappedBy = "refResourceEntity")
    var refResourceTagEntities: List<ResourceTagEntity>? = null

    @get:OneToMany(mappedBy = "refResourceEntity")
    var refResourceUpvoteEntities: List<ResourceUpvoteEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "authorId = $authorId " +
                "revisionsCount = $revisionsCount " +
                "createdAt = $createdAt " +
                "latestRevisionId = $latestRevisionId " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ResourceEntity

        if (id != other.id) return false
        if (authorId != other.authorId) return false
        if (revisionsCount != other.revisionsCount) return false
        if (createdAt != other.createdAt) return false
        if (latestRevisionId != other.latestRevisionId) return false

        return true
    }

}

