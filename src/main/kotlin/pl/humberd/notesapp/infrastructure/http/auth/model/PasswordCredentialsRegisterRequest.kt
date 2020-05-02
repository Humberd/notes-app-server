package pl.humberd.notesapp.infrastructure.http.auth.model

import javax.validation.constraints.NotBlank

class PasswordCredentialsRegisterRequest {
    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var password: String
}
