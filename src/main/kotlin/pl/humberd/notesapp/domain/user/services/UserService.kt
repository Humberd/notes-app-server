package pl.humberd.notesapp.domain.user.services

import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @PostConstruct
    fun init() {
        val user = userRepository.findById("1")
        if (!user.isPresent) {
            println("NO USER")

            return
        }
        println(user.get())

        userRepository.deleteById("1")


    }
}
