package pl.humberd.notesapp.domain.entity.user.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.User
import pl.humberd.notesapp.domain.entity.user.model.UserId
import java.util.*

@Repository
interface UserRepository : RefreshableJpaRepository<User, UserId> {
    @Query("select user from User user where user.nameLc = :nameLc")
    fun findByNameLc(@Param("nameLc") nameLc: String): Optional<User>

    @Query("select case when (count (user) > 0) then true else false end from User user where user.nameLc = :nameLc")
    fun existsByNameLc(@Param("nameLc") nameLc: String): Boolean
}
