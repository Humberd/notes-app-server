package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "auth_google_provider", schema = "public", catalog = "admin")
open class AuthGoogleProviderEntity {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "id", nullable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "email", nullable = false)
    var email: String? = null

    @get:Basic
    @get:Column(name = "picture", nullable = false)
    var picture: String? = null

    @get:Basic
    @get:Column(name = "refresh_token", nullable = false)
    var refreshToken: String? = null

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
                "id = $id " +
                "name = $name " +
                "email = $email " +
                "picture = $picture " +
                "refreshToken = $refreshToken " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AuthGoogleProviderEntity

        if (userId != other.userId) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (picture != other.picture) return false
        if (refreshToken != other.refreshToken) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

