package pl.humberd.notesapp.application.command.tag

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.tag.model.TagCreateCommand
import pl.humberd.notesapp.application.command.tag.model.TagPatchCommand
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Transactional
@Service
class TagCommandHandler(
    private val tagRepository: TagRepository
) {
    fun create(command: TagCreateCommand): Tag {
        val tagExists = tagRepository.existsByNameLc(command.name.toLowerCase())
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

    fun patch(command: TagPatchCommand): Tag {
        val tag = tagRepository.findByIdOrNull(command.id)
        ASSERT_NOT_NULL(tag, command.id)

        tag.also {
            it.name = command.name?: it.name
            it.backgroundColor = command.backgroundColor?: it.backgroundColor
        }

        return tagRepository.save(tag)
    }

}
