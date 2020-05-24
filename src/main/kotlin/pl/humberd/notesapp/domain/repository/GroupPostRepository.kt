package pl.humberd.notesapp.domain.repository

import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupPostEntity
import pl.humberd.notesapp.domain.entity.GroupPostId

interface GroupPostRepository : RefreshableJpaRepository<GroupPostEntity, GroupPostId>
