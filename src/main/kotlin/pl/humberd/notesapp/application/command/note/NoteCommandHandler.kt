package pl.humberd.notesapp.application.command.note

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.application.common.NOT_NULL
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
        val existingNote = noteRepository.findByIdOrNull(command.noteId)
        NOT_NULL(existingNote, command.noteId)

        existingNote.also {
            it.url = command.url ?: it.url
            it.title = command.title ?: it.title
            it.content = command.content ?: it.content
        }

        return noteRepository.save(existingNote)
    }

    fun delete(command: NoteDeleteCommand) {
        val note = noteRepository.findByIdOrNull(command.noteId)
        NOT_NULL(note, command.noteId)

        return noteRepository.deleteById(command.noteId)
    }


}

