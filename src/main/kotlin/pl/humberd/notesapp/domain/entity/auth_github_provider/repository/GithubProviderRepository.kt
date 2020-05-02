package pl.humberd.notesapp.domain.entity.auth_github_provider.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.auth_github_provider.model.GithubProvider
import pl.humberd.notesapp.domain.entity.user.model.UserId

@Repository
interface GithubProviderRepository : RefreshableJpaRepository<GithubProvider, UserId>
