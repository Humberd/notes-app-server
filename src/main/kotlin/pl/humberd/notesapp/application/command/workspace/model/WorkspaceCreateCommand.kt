package pl.humberd.notesapp.application.command.workspace.model

data class WorkspaceCreateCommand(
    val name: String,
    val userId: String
)
