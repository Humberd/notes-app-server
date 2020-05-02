package pl.humberd.notesapp.application.command.auth.google_provider

import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.auth.google_provider.model.GoogleProviderAuthorizationCommand

@Service
class GoogleProviderCommandHandler {
    fun authorize(command: GoogleProviderAuthorizationCommand) {
        println(command)
    }
}
