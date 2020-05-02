package pl.humberd.notesapp.infrastructure.security

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import javax.servlet.http.HttpServletRequest

/**
 * This makes sure there is a `redirect_url` in the state, so that we can redirect user to the frontend page
 */
class Oauth2CustomAuthorizationRequestResolver(
    repo: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {
    private var defaultResolver = DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri)

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        var req = defaultResolver.resolve(request)
        if (req !== null) {
            req = customizeAuthorizationRequest(req)
        }
        return req
    }

    override fun resolve(request: HttpServletRequest?, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        var req = defaultResolver.resolve(request)
        if (req !== null) {
            req = customizeAuthorizationRequest(req)
        }
        return req
    }

    private fun customizeAuthorizationRequest(req: OAuth2AuthorizationRequest): OAuth2AuthorizationRequest {
        val extraParams: MutableMap<String, Any> = HashMap()
        extraParams.putAll(req.additionalParameters)
        extraParams["access_type"] = "offline"

        return OAuth2AuthorizationRequest
            .from(req)
            .additionalParameters(extraParams)
            .state("foobar-bar")
            .build()
    }
}
