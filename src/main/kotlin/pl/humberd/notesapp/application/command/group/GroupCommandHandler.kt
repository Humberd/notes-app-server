package pl.humberd.notesapp.application.command.group

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pl.humberd.notesapp.application.command.group.model.GroupCreateCommand
import pl.humberd.notesapp.application.command.user_group_membership.UserGroupMembershipCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership.model.UserGroupMembershipCreateCommand
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.GroupEntity
import pl.humberd.notesapp.domain.repository.GroupRepository
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class GroupCommandHandler(
    private val groupRepository: GroupRepository,
    private val groupMembershipCommandHandler: UserGroupMembershipCommandHandler
) {

    fun create(command: GroupCreateCommand): GroupEntity {
        val newlyCreatedGroup = groupRepository.save(
            GroupEntity(
                id = IdGenerator.random(GroupEntity::class),
                name = command.name,
                iconUrl = command.iconUrl
            )
        )

        groupMembershipCommandHandler.create(
            UserGroupMembershipCreateCommand(
                userId = command.ownerId,
                groupId = newlyCreatedGroup.id
            )
        )

        return newlyCreatedGroup
    }

}
