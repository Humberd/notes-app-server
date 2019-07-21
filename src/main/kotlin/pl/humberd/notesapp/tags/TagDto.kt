package pl.humberd.notesapp.tags

data class TagDto(
    val id: String,
    val displayName: String,
    val notesCount: Int
) {
    companion object {
        fun fromTag(tag: Tag): TagDto {
            return TagDto(
                id = tag.id,
                displayName = tag.displayName,
                notesCount = tag.notes.size
            )
        }

        fun fromTags(tags: Iterable<Tag>): List<TagDto> {
            return tags.map { fromTag(it) }
                .sortedBy { it.id }
        }
    }
}
