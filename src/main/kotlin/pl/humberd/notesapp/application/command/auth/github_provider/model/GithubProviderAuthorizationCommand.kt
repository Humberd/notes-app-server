package pl.humberd.notesapp.application.command.auth.github_provider.model

data class GithubProviderAuthorizationCommand(
    val id: String,
    val login: String,
    val name: String,
    val email: String,
    val avatarUrl: String,
    val accessToken: String
)
