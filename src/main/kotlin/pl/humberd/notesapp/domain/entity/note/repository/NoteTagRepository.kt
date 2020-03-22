package pl.humberd.notesapp.domain.entity.note.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note.model.NoteTag
import pl.humberd.notesapp.domain.entity.note.model.NoteTagId

@Repository
interface NoteTagRepository: RefreshableJpaRepository<NoteTag, NoteTagId>
