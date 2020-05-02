package pl.humberd.notesapp.infrastructure.http.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.auth.password_credentials.PasswordCretendialsCommandHandler
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsLoginCommand
import pl.humberd.notesapp.application.command.auth.password_credentials.model.PasswordCredentialsRegisterCommand
import pl.humberd.notesapp.application.query.user.UserQueryHandler
import pl.humberd.notesapp.application.query.user.model.UserView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.auth.model.PasswordCredentialsLoginRequest
import pl.humberd.notesapp.infrastructure.http.auth.model.PasswordCredentialsRegisterRequest
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RestController
@RequestMapping("/auth/password-credentials")
class PasswordCredentialsController(
    private val passwordCredentialsCommandHandler: PasswordCretendialsCommandHandler,
    private val userQueryHandler: UserQueryHandler
) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid body: PasswordCredentialsRegisterRequest): ResponseEntity<UserView> {
        val user = passwordCredentialsCommandHandler.register(
            PasswordCredentialsRegisterCommand(
                name = body.name,
                email = body.email,
                password = body.password
            )
        )

        val userView = userQueryHandler.view(user.id)

        return ResponseBuilder.created(userView)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid body: PasswordCredentialsLoginRequest): ResponseEntity<Unit> {
        val jwt = passwordCredentialsCommandHandler.login(
            PasswordCredentialsLoginCommand(
                email = body.email,
                password = body.password
            )
        )

        return ResponseEntity.noContent()
            .header("Authorization", "Bearer $jwt")
            .build()
    }
}
