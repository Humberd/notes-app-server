package pl.humberd.notesapp.infrastructure.http.credentials

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.user.UserCommandHandler
import pl.humberd.notesapp.application.command.user.model.UserWithPasswordCredentialsCreateCommand
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.credentials.model.PasswordCredentialsRegisterRequest
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RestController
@RequestMapping("/auth/password-credentials")
class PasswordCredentialsController(
    private val userCommandHandler: UserCommandHandler,
    private val userQueryHandler: UserQueryHandler
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid body: PasswordCredentialsRegisterRequest): ResponseEntity<UserView> {
        val user = userCommandHandler.create(
            UserWithPasswordCredentialsCreateCommand(
                name = body.name,
                email = body.email,
                password = body.password
            )
        )

        val userView = userQueryHandler.view(user.id)

        return ResponseBuilder.created(userView)
    }
}
