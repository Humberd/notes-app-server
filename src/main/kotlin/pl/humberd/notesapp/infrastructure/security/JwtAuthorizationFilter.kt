package pl.humberd.notesapp.infrastructure.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import pl.humberd.notesapp.application.command.user.UserCommandHandler
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userCommandHandler: UserCommandHandler
) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header === null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val jwt = header.replaceFirst("Bearer ", "")

        val userJwt = try {
            userCommandHandler.validateJwt(jwt)
        } catch (e: Exception) {
            chain.doFilter(request, response)
            return
        }
        println(userJwt)

        SecurityContextHolder.getContext().authentication = JwtAuthorizationToken(
            jwt = jwt,
            userJwt = userJwt
        )
        chain.doFilter(request, response)
    }
}
