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
import pl.humberd.notesapp.application.query.group_post.GroupPostQueryHandler
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewList
import pl.humberd.notesapp.application.query.group_post.model.GroupPostViewListFilter
import pl.humberd.notesapp.application.query.user_group_membership.UserGroupMembershipQueryHandler
import pl.humberd.notesapp.application.query.user_group_membership_invitation.UserGroupMembershipInvitationQueryHandler
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewList
import pl.humberd.notesapp.application.query.user_group_membership_invitation.model.UserGroupMembershipInvitationViewListFilter
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.group.model.GroupCreateRequest
import java.security.Principal
import kotlin.contracts.ExperimentalContracts

@RestController
@RequestMapping("/groups")
@ExperimentalContracts
class GroupHttpController(
    private val groupCommandHandler: GroupCommandHandler,
    private val groupQueryHandler: GroupQueryHandler,
    private val groupViewMapper: GroupViewMapper,
    private val groupPostQueryHandler: GroupPostQueryHandler,
    private val userGroupMembershipInvitationCommandHandler: UserGroupMembershipInvitationCommandHandler,
    private val userGroupMembershipQueryHandler: UserGroupMembershipQueryHandler,
    private val userGroupMembershipInvitationQueryHandler: UserGroupMembershipInvitationQueryHandler
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

    @GetMapping("/{groupId}/invitations")
    fun listGroupInvitations(
        @PathVariable("groupId") groupId: String,
        principal: Principal
    ): ResponseEntity<UserGroupMembershipInvitationViewList> {
        userGroupMembershipQueryHandler.ASSERT_GROUP_MEMBERSHIP(
            userId = principal.name,
            groupId = groupId
        )

        val viewList = userGroupMembershipInvitationQueryHandler.viewList(
            UserGroupMembershipInvitationViewListFilter.ByGroup(groupId)
        )

        return ResponseBuilder.ok(viewList)
    }

    @GetMapping("/{groupId}/posts")
    fun listPosts(
        @PathVariable("groupId") groupId: String,
        principal: Principal
    ): ResponseEntity<GroupPostViewList> {
        userGroupMembershipQueryHandler.ASSERT_GROUP_MEMBERSHIP(
            userId = principal.name,
            groupId = groupId
        )

        val viewList = groupPostQueryHandler.viewList(
            GroupPostViewListFilter(
                groupId = groupId
            )
        )

        return ResponseBuilder.ok(viewList)
    }
}
