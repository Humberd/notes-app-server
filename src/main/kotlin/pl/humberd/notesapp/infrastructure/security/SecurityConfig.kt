package pl.humberd.notesapp.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import pl.humberd.notesapp.application.command.user.UserCommandHandler
import kotlin.contracts.ExperimentalContracts


@ExperimentalContracts
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userCommandHandler: UserCommandHandler
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
            .antMatchers("/auth/password-credentials/register", "/auth/password-credentials/login").permitAll()
            .antMatchers("/healthcheck").permitAll()
            .anyRequest().authenticated()
            .and()
            .headers().frameOptions().disable()

        http
            .addFilter(JwtAuthorizationFilter(authenticationManagerBean(), userCommandHandler))
    }

}
