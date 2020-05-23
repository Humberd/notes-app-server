package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "auth_github_provider", schema = "public", catalog = "admin")
open class AuthGithubProviderEntity {
    @get:Id
    @get:Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    var userId: String? = null

    @get:Basic
    @get:Column(name = "id", nullable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "login", nullable = false)
    var login: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "email", nullable = false)
    var email: String? = null

    @get:Basic
    @get:Column(name = "avatar_url", nullable = false)
    var avatarUrl: String? = null

    @get:Basic
    @get:Column(name = "access_token", nullable = false)
    var accessToken: String? = null

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
                "login = $login " +
                "name = $name " +
                "email = $email " +
                "avatarUrl = $avatarUrl " +
                "accessToken = $accessToken " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AuthGithubProviderEntity

        if (userId != other.userId) return false
        if (id != other.id) return false
        if (login != other.login) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (avatarUrl != other.avatarUrl) return false
        if (accessToken != other.accessToken) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

