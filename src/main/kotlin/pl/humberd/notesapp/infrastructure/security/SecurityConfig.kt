package pl.humberd.notesapp.infrastructure.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import pl.humberd.notesapp.application.command.auth.JwtUtils
import kotlin.contracts.ExperimentalContracts


@ExperimentalContracts
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtils: JwtUtils
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
    }

}
