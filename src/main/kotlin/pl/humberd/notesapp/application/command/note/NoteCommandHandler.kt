package pl.humberd.notesapp.application.command.note

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note.model.*
import pl.humberd.notesapp.application.common.ASSERT_EXISTS
import pl.humberd.notesapp.application.common.ASSERT_NOT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteTag
import pl.humberd.notesapp.domain.entity.note.model.NoteTagId
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.note.repository.NoteTagRepository
import pl.humberd.notesapp.domain.entity.tag.model.Tag
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class NoteCommandHandler(
    private val noteRepository: NoteRepository,
    private val tagRepository: TagRepository,
    private val noteTagRepository: NoteTagRepository
) {

    fun create(command: NoteCreateCommand): Note {
        val entity = noteRepository.saveFlushRefresh(
            Note(
                id = IdGenerator.random(Note::class),
                authorId = command.authorId,
                title = command.title,
                url = command.url,
                content = command.content
            )
        )

        return entity
    }

    fun patch(command: NotePatchCommand): Note {
        val note = noteRepository.findByIdOrNull(command.noteId)
        ASSERT_NOT_NULL(note, command.noteId)

        note.also {
            it.url = command.url ?: it.url
            it.title = command.title ?: it.title
            it.content = command.content ?: it.content
        }

        return noteRepository.save(note)
    }

    fun delete(command: NoteDeleteCommand) {
        val noteExists = noteRepository.existsById(command.noteId)
        ASSERT_EXISTS<Note>(noteExists, command.noteId)

        noteRepository.deleteById(command.noteId)
    }

    fun ensureIsAuthor(command: NoteIsAuthorCommand) {
        val note = noteRepository.findByIdOrNull(command.noteId)
        ASSERT_NOT_NULL(note, command.noteId)
        if (note.authorId != command.userId) {
            throw ForbiddenException(Note::class, command.noteId)
        }
    }

    fun create(command: NoteTagCreateCommand): NoteTag {
        val noteTagId = NoteTagId(
            noteId = command.noteId,
            tagId = command.tagId
        )
        val noteTagExists = noteTagRepository.existsById(noteTagId)
        ASSERT_NOT_EXIST<NoteTag>(noteTagExists, noteTagId.toString())

        val noteExists = noteRepository.existsById(command.noteId)
        ASSERT_EXISTS<Note>(noteExists, command.noteId)

        val tagExists = tagRepository.existsById(command.tagId)
        ASSERT_EXISTS<Tag>(tagExists, command.tagId)


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
        ASSERT_EXISTS<NoteTag>(noteTagExists, noteTagId.toString())

        noteTagRepository.deleteById(noteTagId)
    }

}

