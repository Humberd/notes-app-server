package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepositoryImpl
import pl.humberd.notesapp.domain.repository.GroupPostRepository
import javax.annotation.PostConstruct
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableJpaRepositoryImpl::class)
class NotesAppApplication(
    private val groupPostRepository: GroupPostRepository
) {
    @PostConstruct
    fun initialized() {
        val post = this.groupPostRepository.findById("group-post-1")
        println(post.get())
    }
}

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
