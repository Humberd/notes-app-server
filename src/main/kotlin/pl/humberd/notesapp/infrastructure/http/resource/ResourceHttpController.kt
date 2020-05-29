package pl.humberd.notesapp.infrastructure.http.resource

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.resource.ResourceCommandHandler
import pl.humberd.notesapp.application.command.resource.model.ResourceCreateCommand
import pl.humberd.notesapp.domain.entity.ResourceEntity
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.resource.model.ResourceCreateRequest
import java.security.Principal
import kotlin.contracts.ExperimentalContracts

@RequestMapping("/resources")
@RestController
@ExperimentalContracts
class ResourceHttpController(
    private val resourceCommandHandler: ResourceCommandHandler
) {
    @PostMapping
    fun create(
        @RequestBody body: ResourceCreateRequest,
        principal: Principal
    ): ResponseEntity<ResourceEntity> {
        val resource = resourceCommandHandler.create(
            ResourceCreateCommand(
                authorId = principal.name,
                payload = body.payload,
                tagIds = body.publishSettings.tags.map { it.id },
                omittedGroups = body.publishSettings.omittedGroups.map { it.id }
            )
        )

        return ResponseBuilder.created(resource)
    }
}
