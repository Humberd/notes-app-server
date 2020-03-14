package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class NotesAppApplication(
) {
}

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}

