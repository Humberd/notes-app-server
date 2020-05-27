package pl.humberd.notesapp.infrastructure.http.web_scrape.model

import javax.validation.constraints.NotBlank

class WebScrapePageRequest {
    @NotBlank
    lateinit var url: String
}
