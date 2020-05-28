package pl.humberd.notesapp.domain.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.entity.UserId

@Repository
interface GroupRepository : RefreshableJpaRepository<GroupEntity, GroupId> {

    @Query("select groupEntity from GroupEntity groupEntity left join UserGroupMembershipEntity membership on membership.groupId = groupEntity.id where membership.userId = :userId")
    fun findAllByGroupMembership(@Param("userId") userId: UserId, pageable: Pageable): Page<GroupEntity>

    @Query("select groupEntity from GroupEntity groupEntity left join UserGroupMembershipTagTriggerEntity tagTrigger on tagTrigger.groupId = groupEntity.id where tagTrigger.tagId = :tagId")
    fun findAllByTagTrigger(@Param("tagId") tagId: TagId): List<GroupEntity>
}
