package pl.humberd.notesapp.application.command.tag

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.tag.model.TagCreateCommand
import pl.humberd.notesapp.application.command.tag.model.TagDeleteCommand
import pl.humberd.notesapp.application.command.tag.model.TagIsUsersCommand
import pl.humberd.notesapp.application.command.tag.model.TagPatchCommand
import pl.humberd.notesapp.application.common.ASSERT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import javax.transaction.Transactional
import javax.validation.ValidationException
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class TagCommandHandler(
    private val tagRepository: TagRepository
) {
    fun create(command: TagCreateCommand): Tag {
        VALIDATE_NAME(command.name)

        val tagExists = tagRepository.existsByUserIdAndNameLc(
            userId = command.userId,
            nameLc = command.name.toLowerCase()
        )
        ASSERT_NOT_EXIST<Tag>(tagExists, command.name)

        return tagRepository.save(
            Tag(
                id = IdGenerator.random(Tag::class),
                name = command.name,
                backgroundColor = command.backgoundColor,
                userId = command.userId
            )
        )
    }

    fun createAndRefresh(command: TagCreateCommand): Tag {
        return this.create(command).also {
            tagRepository.saveFlushRefresh(it)
        }
    }

    fun patch(command: TagPatchCommand): Tag {
        val tag = tagRepository.findByIdOrNull(command.id)
        ASSERT_NOT_NULL(tag, command.id)

        if (command.name !== null) {
            VALIDATE_NAME(command.name)
        }

        tag.also {
            it.name = command.name?: it.name
            it.backgroundColor = command.backgroundColor?: it.backgroundColor
        }

        return tagRepository.save(tag)
    }

    fun patchAndRefresh(command: TagPatchCommand): Tag {
        return patch(command).also {
            tagRepository.saveFlushRefresh(it)
        }
    }

    fun delete(command: TagDeleteCommand) {
        val tagExists = tagRepository.existsById(command.tagId)
        ASSERT_EXIST<Tag>(tagExists, command.tagId)

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
            throw ForbiddenException(Tag::class, command.tagId)
        }
    }

}
