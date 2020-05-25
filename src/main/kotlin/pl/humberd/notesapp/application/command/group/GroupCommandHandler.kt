package pl.humberd.notesapp.application.command.group

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.group.model.GroupCreateCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipEntity
import pl.humberd.notesapp.domain.repository.GroupRepository
import pl.humberd.notesapp.domain.repository.UserGroupMembershipRepository

@Service
class GroupCommandHandler(
    private val groupRepository: GroupRepository,
    private val userGroupMembershipRepository: UserGroupMembershipRepository
) {

    fun create(command: GroupCreateCommand): GroupEntity {
        val newlyCreatedGroup = groupRepository.save(
            GroupEntity(
                id = IdGenerator.random(GroupEntity::class),
                name = command.name,
                iconUrl = command.iconUrl
            )
        )

        userGroupMembershipRepository.save(
            UserGroupMembershipEntity(
                userId = command.ownerId,
                groupId = newlyCreatedGroup.id
            )
        )

        return newlyCreatedGroup
    }

}
