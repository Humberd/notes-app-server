package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
@EnableJpaAuditing
class NotesAppApplication

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
