package pl.humberd.notesapp.application.query.group

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.group.model.GroupMinimalView
import pl.humberd.notesapp.application.query.group.model.GroupView
import pl.humberd.notesapp.application.query.user.UserViewMapper
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.entity.GroupId
import pl.humberd.notesapp.domain.repository.GroupRepository
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository

@Service
class GroupViewMapper(
    private val groupRepository: GroupRepository,
    private val groupMembershipRepository: UserGroupMembershipRepository,
    private val userViewMapper: UserViewMapper
) {
    fun mapView(group: GroupEntity) = GroupView(
        id = group.id,
        name = group.name,
        iconUrl = group.iconUrl,
        members = groupMembershipRepository.findAllByGroupId(group.id).map { userViewMapper.mapMinimalView(it.userId) }
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
