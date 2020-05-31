package pl.humberd.notesapp.domain.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.GroupPostId
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
import pl.humberd.notesapp.domain.entity.ResourceRevisionId
import pl.humberd.notesapp.domain.entity.UserId

@Repository
interface ResourceRevisionRepository : RefreshableJpaRepository<ResourceRevisionEntity, ResourceRevisionId> {

    @Query("select resourceRevision from ResourceRevisionEntity resourceRevision left outer join GroupPostUserStateEntity groupPostUserState on resourceRevision.id = groupPostUserState.resourceRevisionId where groupPostUserState.groupPostId = :groupPostId and groupPostUserState.userId = :userId")
    fun findUserSavedRevision(@Param("groupPostId") groupPostId: GroupPostId, @Param("userId") userId: UserId): ResourceRevisionEntity
}
