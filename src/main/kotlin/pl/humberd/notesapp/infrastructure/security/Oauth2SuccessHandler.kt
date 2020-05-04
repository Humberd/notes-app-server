package pl.humberd.notesapp.infrastructure.security

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import pl.humberd.notesapp.application.command.auth.github_provider.GithubProviderCommandHandler
import pl.humberd.notesapp.application.command.auth.github_provider.model.GithubProviderAuthorizationCommand
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
    private val googleProviderCommandHandler: GoogleProviderCommandHandler,
    private val githubProviderCommandHandler: GithubProviderCommandHandler
) : AuthenticationSuccessHandler {
    val logger = LoggerFactory.getLogger(Oauth2SuccessHandler::class.java)

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
        logger.info("provider: ${oauth2Authentication.authorizedClientRegistrationId}")
        logger.info("clientConfig.refreshToken: ${clientConfig?.refreshToken?.tokenValue}")
        logger.info("clientConfig.accessTokne: ${clientConfig.accessToken?.tokenValue}")

        val jwt = try {
             when (oauth2Authentication.authorizedClientRegistrationId) {
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
                "github" -> this.githubProviderCommandHandler.authorize(
                    GithubProviderAuthorizationCommand(
                        id = (oauth2Authentication.principal.attributes["id"] as Int).toString(),
                        login = oauth2Authentication.principal.attributes["login"] as String,
                        name = oauth2Authentication.principal.attributes["name"] as String,
                        email = oauth2Authentication.principal.attributes["email"] as String,
                        avatarUrl = oauth2Authentication.principal.attributes["avatar_url"] as String,
                        accessToken = clientConfig.accessToken.tokenValue
                    )
                )
                else -> throw Error("Provider not supported")
            }
        } catch (e: Exception) {
            logger.error("---- FAILED TO AUTHENTICATE USING PROVIDERS -----")
            logger.error(oauth2Authentication.principal.attributes.toString())

            throw e;
        }

        logger.info("jwt: $jwt")

        val encodedState = request.getParameter("state")
        if (encodedState === null) {
            logger.info("no encoded state")
            return
        }

        val decodedState = String(Base64.getUrlDecoder().decode(encodedState)).split(";")
        if (decodedState.size == 1) {
            logger.info("decoded state: no redirect info $decodedState")
            return
        }

        val frontendUrl = decodedState[1]

        logger.info("frontendUrl: $frontendUrl")

        if (!frontendUrl.startsWith("http://") && !frontendUrl.startsWith("https://")) {
            logger.info("frontendUrl doesnt start with https or http")
            return
        }

        response.sendRedirect("$frontendUrl/?jwt=$jwt")
    }
}
