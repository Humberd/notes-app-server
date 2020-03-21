package pl.humberd.notesapp.infrastructure.http.credentials.model

data class PasswordCredentialsRegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
