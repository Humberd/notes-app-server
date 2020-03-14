package pl.humberd.notesapp.domain.note.services

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.note.models.Note
import pl.humberd.notesapp.domain.note.models.NoteId

@Repository
interface NoteRepository : JpaRepository<Note, NoteId>
