package pl.humberd.notesapp.infrastructure.http.group

import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import pl.humberd.notesapp.application.command.group.GroupCommandHandler
import pl.humberd.notesapp.application.command.group.model.GroupCreateCommand
import pl.humberd.notesapp.application.command.user_group_membership_invitation.UserGroupMembershipInvitationCommandHandler
import pl.humberd.notesapp.application.command.user_group_membership_invitation.model.UserGroupMembershipInvitationCreateCommand
import pl.humberd.notesapp.application.query.group.GroupQueryHandler
import pl.humberd.notesapp.application.query.group.GroupViewMapper
import pl.humberd.notesapp.application.query.group.model.GroupView
import pl.humberd.notesapp.application.query.group.model.GroupViewList
import pl.humberd.notesapp.application.query.group.model.GroupViewListFilter
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.group.model.GroupCreateRequest
import java.security.Principal

@RestController
@RequestMapping("/groups")
class GroupHttpController(
    private val groupCommandHandler: GroupCommandHandler,
    private val groupQueryHandler: GroupQueryHandler,
    private val groupViewMapper: GroupViewMapper,
    private val userGroupMembershipInvitationCommandHandler: UserGroupMembershipInvitationCommandHandler
) {

    @GetMapping
    fun readList(principal: Principal): ResponseEntity<GroupViewList> {
        val listView = groupQueryHandler.listView(
            GroupViewListFilter(
                userId = principal.name
            )
        )

        return ResponseBuilder.ok(listView)
    }


    @PostMapping
    @Transactional
    fun create(
        @RequestBody body: GroupCreateRequest,
        principal: Principal
    ): ResponseEntity<GroupView> {
        val group = groupCommandHandler.create(
            GroupCreateCommand(
                name = body.name,
                iconUrl = body.iconUrl,
                ownerId = principal.name
            )
        )

        body.invitedUsers.forEach { invitedUser ->
            userGroupMembershipInvitationCommandHandler.create(
                UserGroupMembershipInvitationCreateCommand(
                    groupId = group.id,
                    invitedUserId = invitedUser.id,
                    invitedByUserId = principal.name
                )
            )
        }

        return ResponseBuilder.created(groupViewMapper.mapView(group))
    }
}
