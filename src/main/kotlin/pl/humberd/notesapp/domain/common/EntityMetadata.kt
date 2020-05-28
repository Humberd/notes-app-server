package pl.humberd.notesapp.domain.common

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class EntityMetadata {
    @Column(name = "created_at", updatable = false, insertable = false)
    var createdAt = now()
        private set

    @Column(name = "updated_at", updatable = false, insertable = false)
    var updatedAt = now()
        private set

    override fun toString(): String {
        return "EntityMetadata(createdAt=${createdAt.toInstant()}, updatedAt=${updatedAt.toInstant()})"
    }
}
