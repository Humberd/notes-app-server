package pl.humberd.notesapp.infrastructure.http.healthcheck

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder

@RestController
@RequestMapping("/healthcheck")
class HealthcheckController {

    @GetMapping
    fun ping(): ResponseEntity<String> {
        return ResponseBuilder.ok("pong")
    }
}
