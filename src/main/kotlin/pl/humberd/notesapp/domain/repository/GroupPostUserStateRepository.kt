package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupPostUserStateEntity
import pl.humberd.notesapp.domain.entity.GroupPostUserStateEntityPK

@Repository
interface GroupPostUserStateRepository :
    RefreshableJpaRepository<GroupPostUserStateEntity, GroupPostUserStateEntityPK> {

}
