package pl.humberd.notesapp.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import pl.humberd.notesapp.application.command.auth.JwtUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.contracts.ExperimentalContracts


@ExperimentalContracts
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtils: JwtUtils,
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .cors().disable()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http
            .authorizeRequests()
            .antMatchers("/auth/password-credentials/register", "/auth/password-credentials/login").permitAll()
            .antMatchers("/healthcheck").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .headers().frameOptions().disable()

        http
            .addFilter(JwtAuthorizationFilter(authenticationManagerBean(), jwtUtils))

        http.oauth2Login()
            .authorizationEndpoint {
                it.authorizationRequestResolver(CustomAuthorizationRequestResolver(this.clientRegistrationRepository, "/oauth2/authorization"))
            }
            .successHandler { request, response, authentication ->
                val oauth2Authentication = authentication as OAuth2AuthenticationToken
                val clientConfig = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(oauth2Authentication.authorizedClientRegistrationId, oauth2Authentication.principal.name)
                println("success")
            }
    }
}

class CustomAuthorizationRequestResolver(
    repo: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {

    private var defaultResolver: DefaultOAuth2AuthorizationRequestResolver

    init {
        defaultResolver = DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri)
    }

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
