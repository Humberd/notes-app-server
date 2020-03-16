package pl.humberd.notesapp.domain.note.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class NoteService(
    private val noteRepository: NoteRepository
) {
    @PostConstruct
    fun init() {
        val note = noteRepository.findByIdOrNull(
            "xyz"
        )
        println(note)
    }
}
