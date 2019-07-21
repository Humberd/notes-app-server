package pl.humberd.notesapp.notes

import pl.humberd.notesapp.tags.Tag
import javax.persistence.*

@Entity
class Note(
    @Id
    @GeneratedValue
    val id: Long,
    val title: String,
    val content: String,
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "note_tag",
        joinColumns = [JoinColumn(name = "note_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")])
    val tags: Set<Tag> = hashSetOf()
) {}
