package pl.humberd.notesapp.application.query.group

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.model.GroupMinimalView
import pl.humberd.notesapp.application.query.group.model.GroupView
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.repository.GroupRepository

@Service
class GroupViewMapper(
    private val groupRepository: GroupRepository
) {
    fun mapView(group: GroupEntity) = GroupView(
        id = group.id,
        name = group.name,
        iconUrl = group.iconUrl
    )

    fun mapMinimalView(group: GroupEntity) = GroupMinimalView(
        id = group.id,
        name = group.name,
        iconUrl = group.iconUrl
    )

    fun mapMinimalView(groupId: GroupId): GroupMinimalView {
        return mapMinimalView(groupRepository.findById(groupId).get())
    }
}
