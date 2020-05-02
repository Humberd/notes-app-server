package pl.humberd.notesapp.domain.entity.auth_password_credentials.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.auth_password_credentials.model.PasswordCredentials
import pl.humberd.notesapp.domain.entity.user.model.UserId

@Repository
interface PasswordCredentialsRepository : RefreshableJpaRepository<PasswordCredentials, UserId> {
}
