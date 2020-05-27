package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepositoryImpl
import pl.humberd.notesapp.domain.repository.GroupPostRepository
import pl.humberd.notesapp.domain.repository.ResourceRevisionRepository
import javax.annotation.PostConstruct
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableJpaRepositoryImpl::class)
class NotesAppApplication(
    private val groupPostRepository: GroupPostRepository,
    private val resourceRevisionRepository: ResourceRevisionRepository
) {
    @PostConstruct
    fun initialized() {
        val resivion = resourceRevisionRepository.findById("resource-rev1-google_com")
        println(resivion)
    }
}

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
