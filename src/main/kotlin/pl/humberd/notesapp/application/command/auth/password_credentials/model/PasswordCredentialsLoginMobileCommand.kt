package pl.humberd.notesapp.application.command.auth.password_credentials.model

data class PasswordCredentialsLoginMobileCommand(
    val email: String,
    val password: String,
    val pushToken: String
)
