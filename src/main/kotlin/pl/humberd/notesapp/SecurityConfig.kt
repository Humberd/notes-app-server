package pl.humberd.notesapp

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import pl.humberd.notesapp.account.Account
import pl.humberd.notesapp.account.AccountService
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val accountService: AccountService
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
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/oauth2-login-success").permitAll()
            .and()
            .headers().frameOptions().disable()

        http
            .addFilter(JWTAuthorizationFilter(authenticationManagerBean()))
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()

        http
            .oauth2Login()
            .successHandler { request, response, authentication ->
                if (authentication !is OAuth2AuthenticationToken) {
                    throw Error()
                }

                val account: Account = when (authentication.authorizedClientRegistrationId) {
                    "google" -> {
                        authentication.principal.attributes.let {
                            this.accountService.getOrCreateFromGoogle(it["email"] as String, it["sub"] as String)
                        }
                    }
                    else -> {
                        throw Error("Unsupported clientId ${authentication.authorizedClientRegistrationId}")
                    }
                }

                val jwt = Jwts.builder()
                    .addClaims(
                        mapOf(
                            "email" to account.googleAuth?.email
                        )
                    )
                    .setSubject(account.id.toString())
                    .setId(UUID.randomUUID().toString())
                    .setExpiration(Date(System.currentTimeMillis() + 1_000_000_000))
                    .setIssuedAt(Date())
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                    .compact()


                response.sendRedirect("/oauth2-login-success?jwt=$jwt")
            }
    }
}

@RestController
class Oauth2Success {
    @RequestMapping("/oauth2-login-success")
    fun getLoginSuccessPage(): ModelAndView {
        return ModelAndView("oauth2-login-success.html")
    }
}

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        println(authException?.message)
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException?.message)
    }
}

const val JWT_HEADER_NAME = "Authorization"
const val JWT_TOKEN_PREFIX = "Bearer "
const val JWT_SECRET = "my-secret"

class JWTAuthorizationFilter(
    authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(JWT_HEADER_NAME)

        /* When header is null or doesn't start with a required prefix*/
        if (header === null || !header.startsWith(JWT_TOKEN_PREFIX)) {
            println("Request has no $JWT_HEADER_NAME or it doesn't start with $JWT_TOKEN_PREFIX")
            chain.doFilter(req, res)
            return
        }

        val jwtToken = header.replaceFirst(JWT_TOKEN_PREFIX, "")

        SecurityContextHolder.getContext().authentication = getAuthentication(jwtToken)
        chain.doFilter(req, res)
    }

    private fun getAuthentication(jwtToken: String): Authentication {
        val jwtClaims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(jwtToken)
            .getBody()


        return JWTAuthenticationToken(jwtClaims)
    }

}

class JWTAuthenticationToken(
    val claims: Claims
) : AbstractAuthenticationToken(listOf()) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return ""
    }

}
