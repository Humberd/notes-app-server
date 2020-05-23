package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "auth_password_credentials", schema = "public", catalog = "admin")
open class AuthPasswordCredentialsEntity {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "password_hash", nullable = false)
    var passwordHash: String? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:OneToOne(fetch = FetchType.LAZY)
    @get:JoinColumn(name = "user_id", referencedColumnName = "id")
    var refUserEntity: UserEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "userId = $userId " +
                "passwordHash = $passwordHash " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AuthPasswordCredentialsEntity

        if (userId != other.userId) return false
        if (passwordHash != other.passwordHash) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

