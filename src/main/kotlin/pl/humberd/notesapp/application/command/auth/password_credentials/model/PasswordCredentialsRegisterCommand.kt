package pl.humberd.notesapp.application.command.auth.password_credentials.model

data class PasswordCredentialsRegisterCommand(
    val name: String,
    val email: String,
    val password: String
)
