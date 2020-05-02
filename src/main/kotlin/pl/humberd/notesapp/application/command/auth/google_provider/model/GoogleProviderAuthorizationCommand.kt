package pl.humberd.notesapp.application.command.auth.google_provider.model

data class GoogleProviderAuthorizationCommand(
    val accountId: String,
    val accountName: String,
    val email: String,
    val refreshToken: String
)
