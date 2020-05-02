package pl.humberd.notesapp.application.command.auth.password_credentials.model

data class PasswordCredentialsLoginCommand(
    val email: String,
    val password: String
)
