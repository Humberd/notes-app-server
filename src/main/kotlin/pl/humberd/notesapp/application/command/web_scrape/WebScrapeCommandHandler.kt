package pl.humberd.notesapp.application.command.web_scrape

import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.skrape
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.command.web_scrape.model.WebScrapePageCommand
import pl.humberd.notesapp.application.command.web_scrape.model.WebScrapePageMetadata

@Service
class WebScrapeCommandHandler {

    fun getMetadata(command: WebScrapePageCommand): WebScrapePageMetadata {
        val metadata = skrape {
            url = command.url

            extract {
                htmlDocument {
                    WebScrapePageMetadata(
                        title = titleText,
                        description = findFirstOrNull("head > meta[name='description']")?.attribute("content") ?: "",
                        iconUrl = findFirstOrNull("head > meta[property='og:image']")?.attribute("content") ?: ""
                    )
                }
            }
        }

        return metadata
    }
}
