package pl.humberd.notesapp.application.command.tag

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.tag.model.TagCreateCommand
import pl.humberd.notesapp.application.command.tag.model.TagDeleteCommand
import pl.humberd.notesapp.application.command.tag.model.TagIsUsersCommand
import pl.humberd.notesapp.application.command.tag.model.TagPatchCommand
import pl.humberd.notesapp.application.command.user_group_membership_tag_trigger.UserGroupMembershipTagTriggerCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership_tag_trigger.model.UserGroupMembershipTagTriggerCreateCommand
import pl.humberd.notesapp.application.common.asserts.ASSERT_EXIST_GENERIC
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST_GENERIC
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.TagEntity
import pl.humberd.notesapp.domain.repository.TagRepository
import javax.transaction.Transactional
import javax.validation.ValidationException
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class TagCommandHandler(
    private val tagRepository: TagRepository,
    private val tagTriggerCommandHandler: UserGroupMembershipTagTriggerCommandHandler
) {
    fun create(command: TagCreateCommand): TagEntity {
        VALIDATE_NAME(command.name)

        val tagExists = tagRepository.existsByUserIdAndNameLc(
            userId = command.userId,
            nameLc = command.name.toLowerCase()
        )
        ASSERT_NOT_EXIST_GENERIC<TagEntity>(tagExists, command.name)

        val newTagEntity = tagRepository.save(
            TagEntity(
                id = IdGenerator.random(TagEntity::class),
                name = command.name,
                backgroundColor = command.backgoundColor,
                userId = command.userId
            )
        )

        command.publishToGroupIds.forEach { groupId ->
            tagTriggerCommandHandler.create(UserGroupMembershipTagTriggerCreateCommand(
                userId = command.userId,
                tagId = newTagEntity.id,
                groupId = groupId
            ))
        }

        return newTagEntity
    }

    fun patch(command: TagPatchCommand): TagEntity {
        val tag = tagRepository.findByIdOrNull(command.id)
        ASSERT_NOT_NULL(tag, command.id)

        if (command.name !== null) {
            VALIDATE_NAME(command.name)
        }

        tag.also {
            it.name = command.name ?: it.name
            it.backgroundColor = command.backgroundColor ?: it.backgroundColor
        }

        return tagRepository.save(tag)
    }

    fun delete(command: TagDeleteCommand) {
        val tagExists = tagRepository.existsById(command.tagId)
        ASSERT_EXIST_GENERIC<TagEntity>(
            tagExists,
            command.tagId
        )

        tagRepository.deleteById(command.tagId)
    }

    private fun VALIDATE_NAME(name: String) {
        if (name.isBlank()) {
            throw ValidationException("Tag Name cannot be blank")
        }
    }

    fun ensureIsAuthor(command: TagIsUsersCommand) {
        val tag = tagRepository.findByIdOrNull(command.tagId)
        ASSERT_NOT_NULL(tag, command.tagId)
        if (tag.userId != command.userId) {
            throw ForbiddenException(TagEntity::class, command.tagId)
        }
    }

}
