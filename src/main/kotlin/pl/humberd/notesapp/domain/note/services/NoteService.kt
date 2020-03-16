package pl.humberd.notesapp.domain.note.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.transaction.Transactional

@Service
class NoteService(
    private val noteRepository: NoteRepository
) {
    @PostConstruct
    @Transactional
    fun init() {
        val note = noteRepository.findByIdOrNull(
            "xyz"
        )
        println(note)
    }
}
