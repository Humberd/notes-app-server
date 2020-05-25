package pl.humberd.notesapp.application.query.group

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.model.GroupView
import pl.humberd.notesapp.domain.entity.GroupEntity

@Service
class GroupViewMapper {
    fun mapView(group: GroupEntity) = GroupView(
        id = group.id,
        name = group.name,
        iconUrl = group.iconUrl
    )
}
