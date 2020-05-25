package pl.humberd.notesapp.application.query.group

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.query.ListViewExtra
import pl.humberd.notesapp.application.query.group.model.GroupViewList
import pl.humberd.notesapp.application.query.group.model.GroupViewListFilter
import pl.humberd.notesapp.domain.repository.GroupRepository

@Service
class GroupQueryHandler (
    private val groupRepository: GroupRepository,
    private val groupViewMapper: GroupViewMapper
) {
    fun listView(filter: GroupViewListFilter): GroupViewList {
//        val page = groupRepository.findAllByGroupMembership(
//            userId = filter.userId
//        )

        return GroupViewList(
            data = emptyList(),
            extra = ListViewExtra.empty()
        )
    }
}
