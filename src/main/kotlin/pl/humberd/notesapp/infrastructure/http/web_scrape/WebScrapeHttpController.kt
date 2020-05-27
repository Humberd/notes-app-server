package pl.humberd.notesapp.infrastructure.http.web_scrape

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.humberd.notesapp.application.command.web_scrape.WebScrapeCommandHandler
import pl.humberd.notesapp.application.command.web_scrape.model.WebScrapePageCommand
import pl.humberd.notesapp.application.command.web_scrape.model.WebScrapePageMetadata
import pl.humberd.notesapp.infrastructure.common.ResponseBuilder
import pl.humberd.notesapp.infrastructure.http.web_scrape.model.WebScrapePageRequest

@RequestMapping("/web-scrape")
@RestController
class WebScrapeHttpController(
    private val webScrapeCommandHandler: WebScrapeCommandHandler
) {
    @PostMapping
    fun readMetadata(
        @RequestBody body: WebScrapePageRequest
    ): ResponseEntity<WebScrapePageMetadata> {
        val metadata = webScrapeCommandHandler.getMetadata(
            WebScrapePageCommand(
                url = body.url
            )
        )

        return ResponseBuilder.ok(metadata)
    }
}
