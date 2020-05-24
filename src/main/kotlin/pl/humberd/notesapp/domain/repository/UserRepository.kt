package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.UserEntity
import pl.humberd.notesapp.domain.entity.UserId
import java.util.*

@Repository
interface UserRepository : RefreshableJpaRepository<UserEntity, UserId> {
    fun findByNameLc(nameLc: String): Optional<UserEntity>
    fun existsByNameLc(nameLc: String): Boolean
    fun findByEmailLc(emailLc: String): Optional<UserEntity>
    fun existsByEmailLc(emailLc: String): Boolean
}
