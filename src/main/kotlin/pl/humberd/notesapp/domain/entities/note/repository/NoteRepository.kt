package pl.humberd.notesapp.domain.entities.note.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.entities.note.model.Note
import pl.humberd.notesapp.domain.entities.note.model.NoteId

@Repository
interface NoteRepository : JpaRepository<Note, NoteId>
