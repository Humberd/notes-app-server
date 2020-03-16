package pl.humberd.notesapp.domain._utils

import javax.persistence.*

@Entity
@Table(name = "flyway_schema_history", schema = "public", catalog = "admin")
open class FlywaySchemaHistoryEntity {
    @get:Id
    @get:Column(name = "installed_rank", nullable = false)
    var installedRank: Int? = null

    @get:Basic
    @get:Column(name = "version", nullable = true)
    var version: String? = null

    @get:Basic
    @get:Column(name = "description", nullable = false)
    var description: String? = null

    @get:Basic
    @get:Column(name = "type", nullable = false)
    var type: String? = null

    @get:Basic
    @get:Column(name = "script", nullable = false)
    var script: String? = null

    @get:Basic
    @get:Column(name = "checksum", nullable = true)
    var checksum: Int? = null

    @get:Basic
    @get:Column(name = "installed_by", nullable = false)
    var installedBy: String? = null

    @get:Basic
    @get:Column(name = "installed_on", nullable = false)
    var installedOn: java.sql.Timestamp? = null

    @get:Basic
    @get:Column(name = "execution_time", nullable = false)
    var executionTime: Int? = null

    @get:Basic
    @get:Column(name = "success", nullable = false)
    var success: java.lang.Boolean? = null


    override fun toString(): String =
        "Entity of type: ${javaClass.name} ( " +
                "installedRank = $installedRank " +
                "version = $version " +
                "description = $description " +
                "type = $type " +
                "script = $script " +
                "checksum = $checksum " +
                "installedBy = $installedBy " +
                "installedOn = $installedOn " +
                "executionTime = $executionTime " +
                "success = $success " +
                ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as FlywaySchemaHistoryEntity

        if (installedRank != other.installedRank) return false
        if (version != other.version) return false
        if (description != other.description) return false
        if (type != other.type) return false
        if (script != other.script) return false
        if (checksum != other.checksum) return false
        if (installedBy != other.installedBy) return false
        if (installedOn != other.installedOn) return false
        if (executionTime != other.executionTime) return false
        if (success != other.success) return false

        return true
    }

}

