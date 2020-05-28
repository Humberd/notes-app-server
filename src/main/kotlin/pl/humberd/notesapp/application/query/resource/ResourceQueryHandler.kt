package pl.humberd.notesapp.application.query.resource

import org.springframework.stereotype.Service
import pl.humberd.notesapp.domain.repository.ResourceRepository

@Service
class ResourceQueryHandler (
    private val resourceRepository: ResourceRepository,
    private val resourceViewMapper: ResourceViewMapper
) {
}
