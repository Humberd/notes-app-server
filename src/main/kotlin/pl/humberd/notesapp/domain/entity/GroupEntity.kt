package pl.humberd.notesapp.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

typealias GroupId = String

@Entity
@Table(name = "group", schema = "public", catalog = "admin")
class GroupEntity(
    @Id
    @Column(name = "id")
    var id: GroupId,

    @Column(name = "name")
    var name: String,

    @Column(name = "icon_url")
    var iconUrl: String
) {

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

