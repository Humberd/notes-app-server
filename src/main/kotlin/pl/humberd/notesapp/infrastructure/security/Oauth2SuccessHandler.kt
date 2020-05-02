package pl.humberd.notesapp.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import pl.humberd.notesapp.application.command.auth.google_provider.GoogleProviderCommandHandler
import pl.humberd.notesapp.application.command.auth.google_provider.model.GoogleProviderAuthorizationCommand
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.contracts.ExperimentalContracts

/**
 * Logs user with given provider and redirects to the frontend
 */
@ExperimentalContracts
@Configuration
class Oauth2SuccessHandler(
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
    private val googleProviderCommandHandler: GoogleProviderCommandHandler
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oauth2Authentication = authentication as OAuth2AuthenticationToken

        val clientConfig = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            oauth2Authentication.authorizedClientRegistrationId,
            oauth2Authentication.principal.name
        )

        val jwt = when (oauth2Authentication.authorizedClientRegistrationId) {
            "google" -> {
                val emailVerified = oauth2Authentication.principal.attributes["email_verified"] as Boolean
                if (!emailVerified) {
                    throw Error("email not verified")
                }

                this.googleProviderCommandHandler.authorize(
                    GoogleProviderAuthorizationCommand(
                        id = oauth2Authentication.principal.attributes["sub"] as String,
                        name = oauth2Authentication.principal.attributes["name"] as String,
                        email = oauth2Authentication.principal.attributes["email"] as String,
                        picture = oauth2Authentication.principal.attributes["picture"] as String,
                        refreshToken = clientConfig.refreshToken!!.tokenValue
                    )
                )
            }
            else -> throw Error("Provider not supported")
        }

        val encodedState = request.getParameter("state")
        if (encodedState === null) {
            return
        }

        val decodedState = String(Base64.getUrlDecoder().decode(encodedState)).split(";")
        if (decodedState.size == 1) {
            return
        }

        val frontendUrl = decodedState[1]

        if (!frontendUrl.startsWith("http://") && !frontendUrl.startsWith("https://")) {
            return
        }

        response.sendRedirect("$frontendUrl/?jwt=$jwt")
    }
}
