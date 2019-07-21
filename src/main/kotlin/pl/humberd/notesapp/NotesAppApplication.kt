package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class NotesAppApplication

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}


@RestController
class Foo {
    @GetMapping
    fun foo(): String {
        return "pong"
    }
}
