package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "login_password_auth", schema = "public", catalog = "admin")
open class LoginPasswordAuthEntity {
    @get:Id
    @get:Column(name = "user_auth_id", nullable = false, insertable = false, updatable = false)
    var userAuthId: String? = null

    @get:Basic
    @get:Column(name = "email", nullable = false)
    var email: String? = null

    @get:Basic
    @get:Column(name = "email_lc", nullable = false)
    var emailLc: String? = null

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
    @get:JoinColumn(name = "user_auth_id", referencedColumnName = "user_id")
    var refUserAuthEntity: UserAuthEntity? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "userAuthId = $userAuthId " +
                "email = $email " +
                "emailLc = $emailLc " +
                "passwordHash = $passwordHash " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LoginPasswordAuthEntity

        if (userAuthId != other.userAuthId) return false
        if (email != other.email) return false
        if (emailLc != other.emailLc) return false
        if (passwordHash != other.passwordHash) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

