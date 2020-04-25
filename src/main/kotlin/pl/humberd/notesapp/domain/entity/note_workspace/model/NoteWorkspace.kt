package pl.humberd.notesapp.domain.entity.note_workspace.model

import pl.humberd.notesapp.domain.common.EntityMetadata
import pl.humberd.notesapp.domain.entity.note.model.NoteId
import pl.humberd.notesapp.domain.entity.workspace.model.WorkspaceId
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "note_workspace",schema = "public")
class NoteWorkspace(
    @EmbeddedId
    val id: NoteWorkspaceId
) {
    @Embedded
    lateinit var metadata: EntityMetadata
        private set

    override fun toString(): String {
        return "NoteWorkspace(id=$id)"
    }
}

@Embeddable
data class NoteWorkspaceId(
    @Column(name = "note_id")
    val noteId: NoteId,

    @Column(name = "workspace_id")
    val workspaceId: WorkspaceId
) : Serializable
