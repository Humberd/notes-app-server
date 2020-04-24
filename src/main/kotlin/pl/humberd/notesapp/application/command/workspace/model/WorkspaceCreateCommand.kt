package pl.humberd.notesapp.application.command.workspace.model

import javax.validation.constraints.NotBlank

data class WorkspaceCreateCommand(
    @NotBlank
    val name: String,
    val userId: String
)
