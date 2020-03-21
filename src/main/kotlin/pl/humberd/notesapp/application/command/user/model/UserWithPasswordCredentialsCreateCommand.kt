package pl.humberd.notesapp.application.command.user.model

data class UserWithPasswordCredentialsCreateCommand(
    val name: String,
    val email: String,
    val password: String
)
