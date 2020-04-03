package pl.humberd.notesapp.infrastructure.http.my

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import java.security.Principal
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/my/")
@RestController
class MyHttpController(
    private val userQueryHandler: UserQueryHandler
) {

    @GetMapping("/profile")
    fun readUserProfile(
        principal: Principal
    ): ResponseEntity<UserView> {
        val view = userQueryHandler.view(principal.name)

        return ResponseBuilder.ok(view)
    }
}
