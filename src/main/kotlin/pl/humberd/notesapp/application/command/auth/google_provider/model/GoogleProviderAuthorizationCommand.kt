package pl.humberd.notesapp.application.command.auth.google_provider.model

data class GoogleProviderAuthorizationCommand(
    val id: String,
    val name: String,
    val email: String,
    val picture: String,
    val refreshToken: String
)
