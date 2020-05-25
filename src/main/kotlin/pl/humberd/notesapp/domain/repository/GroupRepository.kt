package pl.humberd.notesapp.domain.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.entity.GroupId

@Repository
interface GroupRepository : RefreshableJpaRepository<GroupEntity, GroupId> {

//    @Query("select group from GroupEntity group left join UserGroupMembershipEntity membership on membership.groupId = group.id where membership.userId = :userId")
//    fun findAllByGroupMembership(@Param("userId") userId: UserId): Page<GroupEntity>
}
