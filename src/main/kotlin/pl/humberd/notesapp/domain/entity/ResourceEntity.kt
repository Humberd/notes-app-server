package pl.humberd.notesapp.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

typealias ResourceId = String

@Entity
@Table(name = "resource", schema = "public", catalog = "admin")
class ResourceEntity(
    @Id
    @Column(name = "id")
    var id: ResourceId,

    @Column(name = "author_id")
    var authorId: UserId,

    @Column(name = "created_at")
    var createdAt: java.sql.Timestamp,

    @Column(name = "latest_revision_id")
    var latestRevisionId: ResourceRevisionId
) {

    @Column(name = "revisions_count")
    var revisionsCount: Int = 0

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

