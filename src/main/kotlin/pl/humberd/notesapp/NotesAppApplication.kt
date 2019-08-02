package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import pl.humberd.notesapp.notes.CreateNoteDto
import pl.humberd.notesapp.notes.NotesService
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableJpaAuditing
class NotesAppApplication(
    val notesService: NotesService
) {
    @PostConstruct
    fun init() {
        notesService.create(CreateNoteDto("Raspberry pi Bluetooth", "something", arrayOf("raspberry pi", "linux")))
        notesService.create(CreateNoteDto("How te setup Jenkins ", "", arrayOf("Jenkins", "linux", "shell", "secret")))
        notesService.create(CreateNoteDto("Is spring multithreaded?", "something", arrayOf("Spring", "multithreading")))

        val notes = notesService.readAll(listOf("linux", "spring"), "")
        println(notes)

        val pagedNotes = notesService.readPage(listOf("linux", "Spring"), PageRequest.of(0, 2)).toList()
        println(pagedNotes)

        println(notesService.readAll(listOf(), "Raspberry"))
        println(notesService.readAll(listOf("linux", "spring"), "PI"))
        println(notesService.readAll(listOf("Spring"), "PI"))
    }
}

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}

