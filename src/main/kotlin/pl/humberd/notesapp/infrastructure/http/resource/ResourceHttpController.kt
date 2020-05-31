package pl.humberd.notesapp.infrastructure.http.resource

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.resource.ResourceCommandHandler
import pl.humberd.notesapp.application.command.resource.model.ResourceCreateCommand
import pl.humberd.notesapp.application.command.resource.model.ResourceUpdateCommand
import pl.humberd.notesapp.application.query.resource.ResourceQueryHandler
import pl.humberd.notesapp.application.query.resource.ResourceViewMapper
import pl.humberd.notesapp.application.query.resource.model.ResourceView
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.resource.model.ResourceCreateRequest
import pl.humberd.notesapp.infrastructure.http.resource.model.ResourceUpdateRequest
import java.security.Principal
import javax.validation.Valid
import kotlin.contracts.ExperimentalContracts

@RequestMapping("/resources")
@RestController
@ExperimentalContracts
class ResourceHttpController(
    private val resourceCommandHandler: ResourceCommandHandler,
    private val resourceViewMapper: ResourceViewMapper,
    private val resourceQueryHandler: ResourceQueryHandler
) {
    @PostMapping
    fun create(
        @RequestBody @Valid body: ResourceCreateRequest,
        principal: Principal
    ): ResponseEntity<ResourceView> {
        val resource = resourceCommandHandler.create(
            ResourceCreateCommand(
                authorId = principal.name,
                payload = body.payload,
                tagIds = body.publishSettings.tags.map { it.id },
                omittedGroups = body.publishSettings.omittedGroups.map { it.id }
            )
        )

        val view = resourceViewMapper.mapView(resource)

        return ResponseBuilder.created(view)
    }

    @PutMapping("/{resourceId}")
    fun update(
        @PathVariable resourceId: String,
        @RequestBody @Valid body: ResourceUpdateRequest,
        principal: Principal
    ): ResponseEntity<ResourceView> {
        resourceQueryHandler.ASSERT_IS_AUTHOR(resourceId, principal.name)

        val resource = resourceCommandHandler.update(
            ResourceUpdateCommand(
                resourceId = resourceId,
                payload = body.payload
            )
        )

        val view = resourceViewMapper.mapView(resource)

        return ResponseBuilder.created(view)
    }
}
