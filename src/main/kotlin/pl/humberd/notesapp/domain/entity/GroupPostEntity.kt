package pl.humberd.notesapp.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

typealias GroupPostId = String

@Entity
@Table(name = "group_post", schema = "public", catalog = "admin")
class GroupPostEntity(
    @Id
    @Column(name = "id")
    val id: GroupPostId,

    @Column(name = "group_id")
    val groupId: GroupId,

    @Column(name = "resource_id")
    val resourceId: ResourceId
) {

    override fun toString(): String {
        return "GroupPostEntity(id='$id', groupId='$groupId', resourceId='$resourceId')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroupPostEntity

        if (id != other.id) return false
        if (groupId != other.groupId) return false
        if (resourceId != other.resourceId) return false

        return true
    }

    override fun hashCode(): Int = 42


}

