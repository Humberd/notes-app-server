package pl.humberd.notesapp.application.command.notification

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import pl.humberd.notesapp.domain.entity.UserGroupMembershipInvitationEntity

@Service
class NotificationCommandHandler {

    fun notify(invitation: UserGroupMembershipInvitationEntity) {
        GlobalScope.launch {
            println("sending invitation to: $invitation")
        }
    }
}
