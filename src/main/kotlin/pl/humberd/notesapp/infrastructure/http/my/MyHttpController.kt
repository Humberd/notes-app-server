package pl.humberd.notesapp.infrastructure.http.my

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@RequestMapping("/my/")
@RestController
class MyHttpController {

    @GetMapping("/profile")
    fun readUserProfile(
        httpServletResponse: HttpServletResponse,
        principal: Principal
    ) {

        httpServletResponse.sendRedirect("/users/${principal.name}")
    }

    @GetMapping("/notes")
    fun readNotesList(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        principal: Principal
    ) {
        val builder = UriComponentsBuilder.fromPath("/users/${principal.name}/notes")
        httpServletRequest.parameterMap.entries.forEach {
            builder.queryParam(it.key, it.value.first())
        }

        httpServletResponse.sendRedirect(builder.toUriString())
    }
}
