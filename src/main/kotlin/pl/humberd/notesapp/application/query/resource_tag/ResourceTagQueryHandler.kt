package pl.humberd.notesapp.application.query.resource_tag

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_EXIST
import pl.humberd.notesapp.domain.entity.ResourceId
import pl.humberd.notesapp.domain.entity.ResourceTagEntityPK
import pl.humberd.notesapp.domain.entity.TagId
import pl.humberd.notesapp.domain.repository.ResourceTagRepository
import kotlin.contracts.ExperimentalContracts

@Service
@ExperimentalContracts
data class ResourceTagQueryHandler(
    private val resourceTagRepository: ResourceTagRepository
) {

    fun ASSERT_NOT_EXISTS(tagId: TagId, resourceId: ResourceId) {
        val resourceExists = resourceTagRepository.existsById(
            ResourceTagEntityPK().also {
                it.tagId = tagId
                it.resourceId = resourceId
            }
        )

        ASSERT_NOT_EXIST(resourceExists, "Tag{$tagId} is already assigned to Resource{$resourceId}")
    }
}
