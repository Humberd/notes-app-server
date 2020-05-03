package pl.humberd.notesapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.client.RestTemplate
import pl.humberd.notesapp.domain.configuration.repository.RefreshableJpaRepositoryImpl
import javax.annotation.PostConstruct
import kotlin.contracts.ExperimentalContracts

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableJpaRepositoryImpl::class)
class NotesAppApplication {

    @PostConstruct
    fun postConstruct() {
        val response = RestTemplate().getForEntity("https://google.com", String::class.java)
        println("--------")
        println("--------")
        println("--------")
        println(response.toString())
        println("--------")
        println("--------")
        println("--------")
    }
}

@ExperimentalContracts
fun main(args: Array<String>) {
    runApplication<NotesAppApplication>(*args)
}
