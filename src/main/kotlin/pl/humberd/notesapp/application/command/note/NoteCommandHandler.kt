package pl.humberd.notesapp.application.command.note

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NoteIsAuthorCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.application.common.ASSERT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class NoteCommandHandler(
    private val noteRepository: NoteRepository
) {

    fun create(command: NoteCreateCommand): Note {
        val entity = noteRepository.save(
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

    fun createAndRefresh(command: NoteCreateCommand): Note {
        return create(command).also {
            noteRepository.saveFlushRefresh(it)
        }
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

    fun patchAndRefresh(command: NotePatchCommand): Note {
        return patch(command).also {
            noteRepository.saveFlushRefresh(it)
        }
    }

    fun delete(command: NoteDeleteCommand) {
        val noteExists = noteRepository.existsById(command.noteId)
        ASSERT_EXIST<Note>(noteExists, command.noteId)

        noteRepository.deleteById(command.noteId)
    }

    fun ensureIsAuthor(command: NoteIsAuthorCommand) {
        val note = noteRepository.findByIdOrNull(command.noteId)
        ASSERT_NOT_NULL(note, command.noteId)
        if (note.authorId != command.userId) {
            throw ForbiddenException(Note::class, command.noteId)
        }
    }

}

