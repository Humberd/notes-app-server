package pl.humberd.notesapp.domain.entity.user.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserId
import java.util.*

@Repository
interface UserRepository : RefreshableJpaRepository<User, UserId> {
    fun findByNameLc(nameLc: String): Optional<User>
    fun existsByNameLc(nameLc: String): Boolean
    fun findByEmailLc(emailLc: String): Optional<User>
    fun existsByEmailLc(emailLc: String): Boolean
}
