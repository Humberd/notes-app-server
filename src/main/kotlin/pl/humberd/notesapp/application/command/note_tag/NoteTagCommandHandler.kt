package pl.humberd.notesapp.application.command.note_tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagCreateCommand
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagDeleteCommand
import pl.humberd.notesapp.application.command.tag.TagCommandHandler
import pl.humberd.notesapp.application.command.tag.model.TagCreateCommand
import pl.humberd.notesapp.application.common.ASSERT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.note_tag.model.NoteTag
import pl.humberd.notesapp.domain.entity.note_tag.model.NoteTagId
import pl.humberd.notesapp.domain.entity.note_tag.repository.NoteTagRepository
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class NoteTagCommandHandler(
    private val noteRepository: NoteRepository,
    private val tagRepository: TagRepository,
    private val noteTagRepository: NoteTagRepository,
    private val tagCommandHandler: TagCommandHandler
) {

    fun create(command: NoteTagCreateCommand.ExistingTag): NoteTag {
        val noteTagId = NoteTagId(
            noteId = command.noteId,
            tagId = command.tagId
        )
        val noteTagExists = noteTagRepository.existsById(noteTagId)
        ASSERT_NOT_EXIST<NoteTag>(noteTagExists, noteTagId.toString())

        val noteExists = noteRepository.existsById(command.noteId)
        ASSERT_EXIST<Note>(noteExists, command.noteId)

        val tagExists = tagRepository.existsById(command.tagId)
        ASSERT_EXIST<Tag>(tagExists, command.tagId)


        return noteTagRepository.save(
            NoteTag(
                id = noteTagId
            )
        )
    }

    fun create(command: NoteTagCreateCommand.NotExistingTag): NoteTag {
        val tagExists = tagRepository.existsByNameLc(command.tagName.toLowerCase())
        ASSERT_NOT_EXIST<Tag>(tagExists, command.tagName)

        val tag = tagCommandHandler.create(
            TagCreateCommand(
                userId = command.userId,
                name = command.tagName
            )
        )

        val noteTagId = NoteTagId(
            noteId = command.noteId,
            tagId = tag.id
        )

        val noteExists = noteRepository.existsById(command.noteId)
        ASSERT_EXIST<Note>(noteExists, command.noteId)




        return noteTagRepository.save(
            NoteTag(
                id = noteTagId
            )
        )
    }

    fun delete(command: NoteTagDeleteCommand) {
        val noteTagId = NoteTagId(
            noteId = command.noteId,
            tagId = command.tagId
        )
        val noteTagExists = noteTagRepository.existsById(noteTagId)
        ASSERT_EXIST<NoteTag>(noteTagExists, noteTagId.toString())

        noteTagRepository.deleteById(noteTagId)
    }
}
