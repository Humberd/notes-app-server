package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.humberd.notesapp.notes.CreateNoteDto
import pl.humberd.notesapp.notes.NotesService
import javax.annotation.PostConstruct

@SpringBootApplication
class NotesAppApplication(
    val notesService: NotesService
) {
    @PostConstruct
    fun init() {
        notesService.create(CreateNoteDto("Raspberry pi Bluetooth", "something", arrayOf("raspberry pi", "linux")))
        notesService.create(CreateNoteDto("How te setup Jenkins credentials", "something", arrayOf("Jenkins", "linux", "shell", "secret")))
        notesService.create(CreateNoteDto("Is spring multithreaded?", "something", arrayOf("Spring", "multithreading")))
    }
}

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}

