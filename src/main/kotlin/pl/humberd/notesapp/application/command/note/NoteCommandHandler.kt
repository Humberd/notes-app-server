package pl.humberd.notesapp.application.command.note

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.exceptions.NotFoundError
import javax.transaction.Transactional

@Service
@Transactional
class NoteCommandHandler(
    private val noteRepository: NoteRepository
) {
    fun create(command: NoteCreateCommand): Note {
        return noteRepository.save(
            Note(
                authorId = command.authorId,
                title = command.title,
                url = command.url,
                content = command.content
            )
        )
    }

    fun patch(command: NotePatchCommand): Note {
        val existingNote = noteRepository.findByIdOrNull(command.noteId)
        if (existingNote === null) {
            throw NotFoundError(Note::class, command.noteId)
        }

        existingNote.also {
            it.url = command.url ?: it.url
            it.title = command.title ?: it.title
            it.content = command.content ?: it.content
        }

        return noteRepository.save(existingNote)
    }

    fun delete(command: NoteDeleteCommand) {
        val note = noteRepository.findByIdOrNull(command.noteId)
        if (note === null){
            throw NotFoundError(Note::class, command.noteId)
        }

        return noteRepository.deleteById(command.noteId)
    }


}
