package pl.humberd.notesapp.application.command.user.model

data class PasswordCredentialsAuthorizationCommand(
    val email: String,
    val password: String
)
