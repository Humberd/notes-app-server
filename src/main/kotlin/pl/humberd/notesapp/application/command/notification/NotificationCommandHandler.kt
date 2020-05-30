package pl.humberd.notesapp.application.command.notification

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.common.asserts.ASSERT_NOT_NULL
import pl.humberd.notesapp.application.firebase.FCMService
import pl.humberd.notesapp.application.firebase.model.PushNotificationRequest
import pl.humberd.notesapp.domain.entity.GroupPostEntity
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity
import pl.humberd.notesapp.domain.repository.*
import kotlin.contracts.ExperimentalContracts

private val logger = KotlinLogging.logger {}

@Service
@ExperimentalContracts
class NotificationCommandHandler(
    private val groupRepository: GroupRepository,
    private val groupMembershipRepository: UserGroupMembershipRepository,
    private val pushNotificationTokenRepository: UserPushNotificationTokenRepository,
    private val resourceRevisionRepository: ResourceRevisionRepository,
    private val resourceRepository: ResourceRepository,
    private val fcmService: FCMService
) {

    fun notify(invitation: UserGroupMembershipInvitationEntity) {
        GlobalScope.launch {
            logger.info("sending invitation to: $invitation")
        }
    }

    fun notifyGroupPostCreated(groupPost: GroupPostEntity) {
        GlobalScope.launch {
            val group = groupRepository.findByIdOrNull(groupPost.groupId)
            ASSERT_NOT_NULL(group, groupPost.groupId)

            val resource = resourceRepository.findByIdOrNull(groupPost.resourceId)
            ASSERT_NOT_NULL(resource, groupPost.resourceId)

            val latestResourceRevision = resourceRevisionRepository.findByIdOrNull(resource.latestRevisionId)
            ASSERT_NOT_NULL(latestResourceRevision, resource.latestRevisionId)

            val allGroupMembers = groupMembershipRepository.findAllByGroupId(group.id)
            val groupMembersWithoutAuthor = allGroupMembers.filter { it.userId != resource.authorId }

            val allNotificationTokens = groupMembersWithoutAuthor
                .flatMap { groupMember ->
                    pushNotificationTokenRepository.findAllByUserId(groupMember.userId).also {
                        if (it.size == 0) {
                            logger.info { "User{${groupMember.userId}} does not have any push notification tokens" }
                        }
                    }
                }

            allNotificationTokens.forEach { notificationToken ->
                fcmService.sendMessageToToken(
                    PushNotificationRequest(
                        title = latestResourceRevision.payload.title,
                        message = "${latestResourceRevision.payload.url} ${latestResourceRevision.payload.description}",
                        token = notificationToken.token,
                        topic = "doesnt matter for now"
                    )
                )
                logger.info { "Sending notification to: ${notificationToken.userId}, because there was new post in group ${group.id}" }
            }

            logger.info { "Sent ${allNotificationTokens.size} Notifications" }
        }
    }
}
