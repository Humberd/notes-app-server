package pl.humberd.notesapp.infrastructure.http.auth.model

import javax.validation.constraints.NotBlank

class PasswordCredentialsLoginMobileRequest {
    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var password: String

    @NotBlank
    lateinit var pushToken: String
}
