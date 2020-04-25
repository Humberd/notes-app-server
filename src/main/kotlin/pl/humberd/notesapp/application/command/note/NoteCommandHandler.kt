package pl.humberd.notesapp.application.command.note

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.note.model.NoteCreateCommand
import pl.humberd.notesapp.application.command.note.model.NoteDeleteCommand
import pl.humberd.notesapp.application.command.note.model.NoteIsAuthorCommand
import pl.humberd.notesapp.application.command.note.model.NotePatchCommand
import pl.humberd.notesapp.application.command.note_tag.NoteTagCommandHandler
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagCreateCommand
import pl.humberd.notesapp.application.command.note_tag.model.NoteTagDeleteCommand
import pl.humberd.notesapp.application.command.note_workspace.NoteWorkspaceCommandHandler
import pl.humberd.notesapp.application.command.note_workspace.model.NoteWorkspaceCreateCommand
import pl.humberd.notesapp.application.common.ASSERT_EXIST
import pl.humberd.notesapp.application.common.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.common.applyPatch
import pl.humberd.notesapp.application.exceptions.ForbiddenException
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.repository.NoteRepository
import pl.humberd.notesapp.domain.entity.tag.repository.TagRepository
import javax.transaction.Transactional
import kotlin.contracts.ExperimentalContracts

@Service
@Transactional
@ExperimentalContracts
class NoteCommandHandler(
    private val noteRepository: NoteRepository,
    private val noteTagCommandHandler: NoteTagCommandHandler,
    private val noteWorkspaceCommandHandler: NoteWorkspaceCommandHandler,
    private val tagRepository: TagRepository
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

        command.tags.forEach { tag ->
            noteTagCommandHandler.create(
                NoteTagCreateCommand(
                    noteId = entity.id,
                    tagName = tag.name,
                    userId = command.authorId
                )
            )
        }

        command.workspaces.forEach { workspace ->
            noteWorkspaceCommandHandler.create(
                NoteWorkspaceCreateCommand(
                    noteId = entity.id,
                    workspaceId = workspace.id
                )
            )
        }

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
            it.url = it.url.applyPatch(command.url)
            it.title = it.title.applyPatch(command.title)
            it.content = it.content.applyPatch(command.content)
        }

        if (command.tags !== null) {
            val appliedTags = command.tags.associateBy({ it.name.toLowerCase() }, { it })
            val appliedTagNames = appliedTags.keys

            val existingTags =
                tagRepository.findAllByNote(note.id, Pageable.unpaged()).content.associateBy({ it.nameLc }, { it })
            val existingTagNames = existingTags.keys

            val tagsToRemove = existingTagNames.subtract(appliedTagNames)
            val tagsToAdd = appliedTagNames.subtract(existingTagNames)

            tagsToRemove.forEach {
                noteTagCommandHandler.delete(NoteTagDeleteCommand(
                    noteId = note.id,
                    tagId = existingTags[it]!!.id
                ))
            }

            tagsToAdd.forEach {
                noteTagCommandHandler.create(
                    NoteTagCreateCommand(
                        noteId = note.id,
                        userId = command.userId,
                        tagName = appliedTags[it]!!.name
                    )
                )
            }
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

