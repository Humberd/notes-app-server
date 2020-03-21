package pl.humberd.notesapp.domain.entity.models

import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Embeddable
class EntityMetadata {
    @Column(name = "created_at", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Calendar = Calendar.getInstance()
        private set

    @Column(name = "updated_at", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Calendar = Calendar.getInstance()
        private set

    override fun toString(): String {
        return "EntityMetadata(createdAt=${createdAt.toInstant()}, updatedAt=${updatedAt.toInstant()})"
    }
}
