package pl.humberd.notesapp.infrastructure.http.tag

import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.tag.TagCommandHandler
import pl.humberd.notesapp.application.command.tag.model.TagCreateCommand
import pl.humberd.notesapp.application.command.tag.model.TagDeleteCommand
import pl.humberd.notesapp.application.command.tag.model.TagIsUsersCommand
import pl.humberd.notesapp.application.command.tag.model.TagPatchCommand
import pl.humberd.notesapp.application.query.tag.TagQueryHandler
import pl.humberd.notesapp.application.query.tag.model.TagListFilter
import pl.humberd.notesapp.application.query.tag.model.TagView
import pl.humberd.notesapp.application.query.tag.model.TagViewList
import pl.humberd.notesapp.domain.entity.tag.model.TagId
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.tag.model.TagCreateRequest
import pl.humberd.notesapp.infrastructure.http.tag.model.TagPatchRequest
import java.security.Principal
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/tags")
@RestController
class TagController(
    private val tagQueryHandler: TagQueryHandler,
    private val tagCommandHandler: TagCommandHandler
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

    @PostMapping
    fun create(
        @Valid @RequestBody body: TagCreateRequest,
        principal: Principal
    ): ResponseEntity<TagView> {
        val tag = tagCommandHandler.createAndRefresh(
            TagCreateCommand(
                userId = principal.name,
                name = body.name,
                backgoundColor = body.backgroundColor
            )
        )

        return ResponseBuilder.created(tagQueryHandler.view(tag.id))
    }

    @PatchMapping("/{id}")
    fun patch(
        @PathVariable("id") id: TagId,
        @RequestBody body: TagPatchRequest,
        principal: Principal
    ): ResponseEntity<TagView> {
        tagCommandHandler.ensureIsAuthor(
            TagIsUsersCommand(
                tagId = id,
                userId = principal.name
            )
        )

        tagCommandHandler.patchAndRefresh(
            TagPatchCommand(
                id = id,
                name = body.name,
                backgroundColor = body.backgroundColor
            )
        )

        val tagView = tagQueryHandler.view(id)

        return ResponseBuilder.updated(tagView)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: TagId,
        principal: Principal
    ): ResponseEntity<Unit> {
        tagCommandHandler.ensureIsAuthor(
            TagIsUsersCommand(
                tagId = id,
                userId = principal.name
            )
        )

        tagCommandHandler.delete(
            TagDeleteCommand(
                tagId = id
            )
        )

        return ResponseBuilder.noContent()
    }

}
