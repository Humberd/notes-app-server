package pl.humberd.notesapp.domain.entity.user.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.user.model.UserId
import pl.humberd.notesapp.domain.entity.user.model.UserPasswordCredentials

@Repository
interface UserPasswordCredentialsRepository : RefreshableJpaRepository<UserPasswordCredentials, UserId>
