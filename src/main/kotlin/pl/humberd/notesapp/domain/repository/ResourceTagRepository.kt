package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.ResourceTagEntity
import pl.humberd.notesapp.domain.entity.ResourceTagEntityPK

@Repository
interface ResourceTagRepository : RefreshableJpaRepository<ResourceTagEntity, ResourceTagEntityPK>
