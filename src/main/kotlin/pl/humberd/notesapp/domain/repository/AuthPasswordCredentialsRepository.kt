package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.AuthPasswordCredentialsEntity
import pl.humberd.notesapp.domain.entity.UserId

@Repository
interface AuthPasswordCredentialsRepository : RefreshableJpaRepository<AuthPasswordCredentialsEntity, UserId> {
}
