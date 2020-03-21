package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepositoryImpl
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableJpaRepositoryImpl::class)
class NotesAppApplication

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
