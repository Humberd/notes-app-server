package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import pl.humberd.notesapp.account.AccountService
import pl.humberd.notesapp.notes.NotesService

@SpringBootApplication
@EnableJpaAuditing
class NotesAppApplication(
    val notesService: NotesService,
    val accountService: AccountService
) {
//    @PostConstruct
//    fun init1() {
//        notesService.create(CreateNoteDto("Raspberry pi Bluetooth", "something", arrayOf("raspberry pi", "linux")))
//        notesService.create(CreateNoteDto("How te setup Jenkins ", "", arrayOf("Jenkins", "linux", "shell", "secret")))
//        notesService.create(CreateNoteDto("Is spring multithreaded?", "something", arrayOf("Spring", "multithreading")))
//
//        val notes = notesService.readAll(listOf("linux", "spring"), "")
//        println(notes)
//
//        val pagedNotes = notesService.readPage(listOf("linux", "Spring"), PageRequest.of(0, 2)).toList()
//        println(pagedNotes)
//
//        println(notesService.readAll(listOf(), "Raspberry"))
//        println(notesService.readAll(listOf("linux", "spring"), "PI"))
//        println(notesService.readAll(listOf("Spring"), "PI"))
//    }
//
//    @PostConstruct
//    fun init2() {
//        val account1 = accountService.getOrCreateFromGoogle("admin@gmail.com", "41241241")
//        val account2 = accountService.getOrCreateFromGoogle("admin@gmail.com", "41241241")
//
//        println("foo")
////        println(account)
//    }
}

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}

