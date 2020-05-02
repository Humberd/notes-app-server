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
import pl.humberd.notesapp.application.command.auth.JwtUtils
import pl.humberd.notesapp.application.command.auth.google_provider.GoogleProviderCommandHandler
import pl.humberd.notesapp.application.command.auth.google_provider.model.GoogleProviderAuthorizationCommand
import java.util.*
import kotlin.contracts.ExperimentalContracts


@ExperimentalContracts
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtils: JwtUtils,
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
    private val googleProviderCommandHandler: GoogleProviderCommandHandler
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
                it.authorizationRequestResolver(
                    Oauth2CustomAuthorizationRequestResolver(
                        repo = clientRegistrationRepository,
                        authorizationRequestBaseUri = "/oauth2/authorization"
                    )
                )
            }
            .successHandler { request, response, authentication ->
                val oauth2Authentication = authentication as OAuth2AuthenticationToken

                val clientConfig = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
                    oauth2Authentication.authorizedClientRegistrationId,
                    oauth2Authentication.principal.name
                )

                val jwt = when (oauth2Authentication.authorizedClientRegistrationId) {
                    "google" -> this.googleProviderCommandHandler.authorize(
                        GoogleProviderAuthorizationCommand(
                            accountId = oauth2Authentication.principal.attributes["sub"] as String,
                            accountName = oauth2Authentication.principal.attributes["name"] as String,
                            email = oauth2Authentication.principal.attributes["email"] as String,
                            refreshToken = clientConfig.refreshToken!!.tokenValue
                        )
                    )
                    else -> throw Error("Provider not supported")
                }

                val encodedState = request.getParameter("state")
                if (encodedState === null) {
                    return@successHandler
                }

                val decodedState = Base64.getUrlDecoder().decode(encodedState).toString().split(";")
                if (decodedState.size == 1) {
                    return@successHandler
                }

                val frontendUrl = decodedState[1]

                if (!frontendUrl.startsWith("http://") || !frontendUrl.startsWith("https://")) {
                    return@successHandler
                }

                response.sendRedirect("$frontendUrl/?jwt=$jwt")
            }
    }
}

