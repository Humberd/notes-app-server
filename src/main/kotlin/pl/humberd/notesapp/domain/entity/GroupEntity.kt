package pl.humberd.notesapp.domain.entity

import javax.persistence.*

@Entity
@Table(name = "group", schema = "public", catalog = "admin")
open class GroupEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "icon_url", nullable = false)
    var iconUrl: String? = null

    @get:OneToMany(mappedBy = "refGroupEntity")
    var refGroupPostEntities: List<GroupPostEntity>? = null

    @get:OneToMany(mappedBy = "refGroupEntity")
    var refUserGroupMembershipEntities: List<UserGroupMembershipEntity>? = null

    @get:OneToMany(mappedBy = "refGroupEntity")
    var refUserGroupMembershipTagTriggerEntities: List<UserGroupMembershipTagTriggerEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "iconUrl = $iconUrl " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GroupEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (iconUrl != other.iconUrl) return false

        return true
    }

}

