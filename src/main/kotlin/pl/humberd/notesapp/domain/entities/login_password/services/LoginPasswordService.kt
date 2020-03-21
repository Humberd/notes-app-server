package pl.humberd.notesapp.domain.entities.login_password.services

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class LoginPasswordService(
    private val loginPasswordRepository: LoginPasswordRepository
) {
    @PostConstruct
    fun init() {
        val loginPasswordAuth = loginPasswordRepository.findByIdOrNull("1")
        println(loginPasswordAuth)
    }
}
