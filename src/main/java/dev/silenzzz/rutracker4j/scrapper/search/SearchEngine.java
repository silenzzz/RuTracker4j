package dev.silenzzz.rutracker4j.scrapper.search;

import dev.silenzzz.rutracker4j.scrapper.parse.PageParser;
import dev.silenzzz.rutracker4j.scrapper.search.exception.SearchException;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchQuery;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;

import java.util.stream.Collectors;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@RequiredArgsConstructor
public class SearchEngine {

    private final PageParser parser;
    private final URIBuilder uriBuilder;

    public SearchEngine(PageParser parser) {
        this.parser = parser;
        this.uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("rutracker.org")
                .setPath("/forum/tracker.php");
    }

    public SearchResult search(SearchQuery query) throws SearchException {
        String url;

        try {
            url = uriBuilder
                    .addParameter("f", query.getCategories().stream()
                            .map(c -> String.valueOf(c.getId()))
                            .collect(Collectors.joining(",")))
                    .addParameter("nm", query.getText())
                    .build()
                    .toString();

            return parser.parseSearchResultPage(url);
        } catch (Exception e) {
            throw new SearchException(e);
        }
    }
}
