package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import pl.humberd.notesapp.domain.common.IdGenerator
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepositoryImpl
import pl.humberd.notesapp.domain.entity.ResourceRevisionEntity
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
        val post = this.groupPostRepository.findById("group-post-1")
        println(post.get())

        resourceRevisionRepository.save(
            ResourceRevisionEntity(
                id = IdGenerator.random(ResourceRevisionEntity::class),
                changeKind = ResourceRevisionEntity.ResourceChangeKind.DELETE,
                payload = ResourceRevisionEntity.LinkPayload("https://google.com"),
                type = ResourceRevisionEntity.ResourceType.LINK,
                resourceId = "resource-google_com"
            )
        )
    }
}

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
