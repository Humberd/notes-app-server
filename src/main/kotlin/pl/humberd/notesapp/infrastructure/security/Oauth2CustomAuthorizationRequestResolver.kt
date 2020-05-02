package pl.humberd.notesapp.infrastructure.security

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.security.crypto.keygen.StringKeyGenerator
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashMap

/**
 * This makes sure there is a `redirect_url` in the state, so that we can redirect user to the frontend page
 */
class Oauth2CustomAuthorizationRequestResolver(
    repo: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {
    private var defaultResolver = DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri)
    private val stateGenerator: StringKeyGenerator = Base64StringKeyGenerator(Base64.getUrlEncoder())

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        var req = defaultResolver.resolve(request)
        if (req !== null) {
            req = customizeAuthorizationRequest(req, request)
        }
        return req
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        var req = defaultResolver.resolve(request)
        if (req !== null) {
            req = customizeAuthorizationRequest(req, request)
        }
        return req
    }

    private fun customizeAuthorizationRequest(
        req: OAuth2AuthorizationRequest,
        request: HttpServletRequest?
    ): OAuth2AuthorizationRequest {
        val extraParams: MutableMap<String, Any> = HashMap()
        extraParams.putAll(req.additionalParameters)
        extraParams["access_type"] = "offline"

        val redirectUri = request?.getParameter("redirect_uri")
        val nonce = stateGenerator.generateKey()

        return OAuth2AuthorizationRequest
            .from(req)
            .additionalParameters(extraParams)
            .also {
                val newState = if (redirectUri !== null) "$nonce;$redirectUri" else nonce
                Base64.getUrlEncoder().encodeToString(newState.toByteArray())
            }
            .build()
    }
}
