package pl.humberd.notesapp.domain.entity.note.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId

@Repository
interface NoteRepository : RefreshableJpaRepository<Note, NoteId>
