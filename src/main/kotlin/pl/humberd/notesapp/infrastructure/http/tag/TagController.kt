package pl.humberd.notesapp.infrastructure.http.tag

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.query.tag.TagQueryHandler
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.tag.model.TagViewList
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import java.security.Principal

@RequestMapping("/tags")
@RestController
class TagController(
    private val tagQueryHandler: TagQueryHandler
) {

    @GetMapping
    fun readList(
        pageable: Pageable,
        principal: Principal
    ): ResponseEntity<TagViewList> {
        val viewList = tagQueryHandler.listView(
            TagListFilter.ByUser(
                pageable = pageable,
                userId = principal.name
            )
        )


        return ResponseBuilder.ok(viewList)
    }

}
