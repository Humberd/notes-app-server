package pl.humberd.notesapp.domain.entity.note.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.entity.note.model.Note
import pl.humberd.notesapp.domain.entity.note.model.NoteId

@Repository
interface NoteRepository : JpaRepository<Note, NoteId>
