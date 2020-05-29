package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.entity.UserGroupMembershipTagTriggerEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipTagTriggerEntityPK

@Repository
interface UserGroupMembershipTagTriggerRepository :
    RefreshableJpaRepository<UserGroupMembershipTagTriggerEntity, UserGroupMembershipTagTriggerEntityPK> {

    fun findAllByTagId(tagId: TagId): List<UserGroupMembershipTagTriggerEntity>
}
