package pl.humberd.notesapp.domain._utils.models

import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Embeddable
data class EntityMetadata(
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Calendar,

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    val updatedAt: Calendar
) {
    override fun toString(): String {
        return "EntityMetadata(createdAt=${createdAt.toInstant()}, updatedAt=${updatedAt.toInstant()})"
    }
}
