package pl.humberd.notesapp.infrastructure.http.auth.model

import javax.validation.constraints.NotBlank

class PasswordCredentialsLoginRequest {
    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var password: String
}
