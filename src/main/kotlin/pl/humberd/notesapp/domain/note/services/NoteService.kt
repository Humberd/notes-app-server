package pl.humberd.notesapp.domain.note.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.domain.note.models.NoteId
import javax.annotation.PostConstruct

@Service
class NoteService(
    private val noteRepository: NoteRepository
) {
    @PostConstruct
    fun init() {
        val note = noteRepository.findByIdOrNull(
            NoteId(
                "xyz", "1"
            )
        )
        println(note)
    }
}
