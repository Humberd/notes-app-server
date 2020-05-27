package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionId

@Repository
interface ResourceRevisionRepository : RefreshableJpaRepository<ResourceRevisionEntity, ResourceRevisionId>
