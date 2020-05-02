package pl.humberd.notesapp.domain.entity.auth_google_provider.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.auth_google_provider.model.GoogleProvider
import pl.humberd.notesapp.domain.entity.user.model.UserId

@Repository
interface GoogleProviderRepository : RefreshableJpaRepository<GoogleProvider, UserId>
