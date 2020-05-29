package pl.humberd.notesapp.application.query.group_post

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.GroupViewMapper
import pl.humberd.notesapp.application.query.group_post.model.GroupPostView
import pl.humberd.notesapp.application.query.resource.ResourceViewMapper
import pl.humberd.notesapp.domain.entity.GroupPostEntity
import pl.humberd.notesapp.domain.entity.UserId

@Service
class GroupPostViewMapper(
    private val groupViewMapper: GroupViewMapper,
    private val resourceViewMapper: ResourceViewMapper
) {

    fun mapView(entity: GroupPostEntity, userId: UserId) = GroupPostView(
        id = entity.id,
        group = groupViewMapper.mapMinimalView(entity.groupId),
        resource = resourceViewMapper.mapView(entity.resourceId, userId)
    )
}
