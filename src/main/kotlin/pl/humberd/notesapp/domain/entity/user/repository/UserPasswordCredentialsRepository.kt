package pl.humberd.notesapp.domain.entity.user.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.user.model.UserPasswordCredentials

@Repository
interface UserPasswordCredentialsRepository : RefreshableJpaRepository<UserPasswordCredentials, UserId> {

    @Query("select case when (count (user) > 0) then true else false end from UserPasswordCredentials user where user.emailLc = :emailLc")
    fun existsByEmailLc(@Param("emailLc") emailLc: String): Boolean
}
