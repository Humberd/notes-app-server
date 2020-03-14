package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import pl.humberd.notesapp.domain.user.services.UserService

@SpringBootApplication
@EnableJpaAuditing
class NotesAppApplication(
    val userService: UserService
) {

}

fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}

