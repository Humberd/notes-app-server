package pl.humberd.notesapp.infrastructure.http.workspace.model

import javax.validation.constraints.NotBlank

class WorkspaceCreateRequest {
    @NotBlank
    lateinit var name: String
}
