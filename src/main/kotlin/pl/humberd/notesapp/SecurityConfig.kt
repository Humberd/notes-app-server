package pl.humberd.notesapp

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
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
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!
            .cors().disable()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilter(JWTAuthorizationFilter(authenticationManagerBean()))
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .antMatcher("h2-console/**").anonymous()
            .and()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .oauth2Login()
            .successHandler { request, response, authentication ->
                response.addHeader("Authorization", "Bearer xxxx")
            }
            .and()
            .logout()
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
            logger.trace { "Request has no $JWT_HEADER_NAME or it doesn't start with $JWT_TOKEN_PREFIX" }
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
