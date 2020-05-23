package pl.humberd.notesapp.domain.generated

import javax.persistence.*

@Entity
@Table(name = "user", schema = "public", catalog = "admin")
open class UserEntity {
    @get:Id
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: String? = null

    @get:Basic
    @get:Column(name = "name", nullable = false)
    var name: String? = null

    @get:Basic
    @get:Column(name = "name_lc", nullable = false)
    var nameLc: String? = null

    @get:Basic
    @get:Column(name = "email", nullable = false)
    var email: String? = null

    @get:Basic
    @get:Column(name = "email_lc", nullable = false)
    var emailLc: String? = null

    @get:Basic
    @get:Column(name = "created_at", nullable = false)
    var createdAt: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "updated_at", nullable = false)
    var updatedAt: java.sql.Timestamp? = null

    @get:OneToOne(mappedBy = "refUserEntity")
    var refAuthGithubProviderEntity: AuthGithubProviderEntity? = null

    @get:OneToOne(mappedBy = "refUserEntity")
    var refAuthGoogleProviderEntity: AuthGoogleProviderEntity? = null

    @get:OneToOne(mappedBy = "refUserEntity")
    var refAuthPasswordCredentialsEntity: AuthPasswordCredentialsEntity? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refGroupPostUserStateEntities: List<GroupPostUserStateEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refResourceEntities: List<ResourceEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refResourceUpvoteEntities: List<ResourceUpvoteEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refTagEntities: List<TagEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refUserGroupMembershipEntities: List<UserGroupMembershipEntity>? = null

    @get:OneToMany(mappedBy = "refUserEntity")
    var refUserGroupMembershipTagTriggerEntities: List<UserGroupMembershipTagTriggerEntity>? = null

    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "id = $id " +
                "name = $name " +
                "nameLc = $nameLc " +
                "email = $email " +
                "emailLc = $emailLc " +
                "createdAt = $createdAt " +
                "updatedAt = $updatedAt " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (nameLc != other.nameLc) return false
        if (email != other.email) return false
        if (emailLc != other.emailLc) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false

        return true
    }

}

