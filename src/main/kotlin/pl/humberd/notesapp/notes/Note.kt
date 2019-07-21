package pl.humberd.notesapp.notes

import pl.humberd.notesapp.tags.Tag
import javax.persistence.*

@Entity
data class Note(
    @Id
    @GeneratedValue
    val id: Long,
    val title: String,
    val content: String,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "note_tag",
        joinColumns = [JoinColumn(name = "note_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
    val tags: Set<Tag>
)
