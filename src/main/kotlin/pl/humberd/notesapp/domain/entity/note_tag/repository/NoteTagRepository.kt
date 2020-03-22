package pl.humberd.notesapp.domain.entity.note_tag.repository

import org.springframework.stereotype.Repository
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepository
import pl.humberd.notesapp.domain.entity.note_tag.model.NoteTag
import pl.humberd.notesapp.domain.entity.note_tag.model.NoteTagId

@Repository
interface NoteTagRepository: RefreshableJpaRepository<NoteTag, NoteTagId>
