package pl.humberd.notesapp.application.query.tag.model

import org.springframework.data.domain.Pageable
import pl.humberd.notesapp.domain.entity.UserId

sealed class TagListFilter(
    val pageable: Pageable
) {

    class ByUser(
        pageable: Pageable,
        val userId: UserId
    ) : TagListFilter(pageable)

}
