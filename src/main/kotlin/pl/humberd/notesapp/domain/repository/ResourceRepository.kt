package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.domain.entity.ResourceId

@Repository
interface ResourceRepository : RefreshableJpaRepository<ResourceEntity, ResourceId>
