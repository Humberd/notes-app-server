package pl.humberd.notesapp.tags

import pl.humberd.notesapp.notes.Note
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class Tag(
    @Id
    val id: String,
    val displayName: String,
    @ManyToMany(mappedBy = "tags")
    val notes: Set<Note> = hashSetOf()
)
