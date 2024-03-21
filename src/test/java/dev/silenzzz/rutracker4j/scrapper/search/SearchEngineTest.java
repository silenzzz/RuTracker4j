package dev.silenzzz.rutracker4j.scrapper.search;

import dev.silenzzz.rutracker4j.scrapper.net.AccountCredentials;
import dev.silenzzz.rutracker4j.scrapper.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.scrapper.parse.PageParser;
import dev.silenzzz.rutracker4j.scrapper.search.exception.InvalidPaginationException;
import dev.silenzzz.rutracker4j.scrapper.search.exception.SearchException;
import dev.silenzzz.rutracker4j.scrapper.search.model.Pagination;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchQuery;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchResult;
import dev.silenzzz.rutracker4j.scrapper.search.ref.CategoryReference;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Timeout(30)
@EnabledIf(value = "ifEnvironmentVariablesSet")
class SearchEngineTest {

    private JSoupHttpClient client;
    private PageParser parser;
    private SearchEngine searchEngine;

    private static boolean ifEnvironmentVariablesSet() {
        return System.getenv("USERNAME") != null && System.getenv("PASSWORD") != null;
    }

    @BeforeEach
    @Test
    void shouldInitializeExtractor() {
        assertDoesNotThrow(() -> {
                    client = new JSoupHttpClient(
                            new AccountCredentials(
                                    System.getenv("USERNAME"),
                                    System.getenv("PASSWORD")
                            ),
                            null
                    );
                    parser = new PageParser(client);
                    searchEngine = new SearchEngine(parser);
                }
        );
    }

    @Test
    void shouldPerformSearch() throws SearchException, InvalidPaginationException {
        SearchQuery query = SearchQuery.builder()
                .text("plane")
                .categories(
                        List.of(
                                new CategoryReference(1950, "Not necessary"),
                                new CategoryReference(2091, "Not necessary"),
                                new CategoryReference(2092, "Not necessary"),
                                new CategoryReference(2093, "Not necessary"),
                                new CategoryReference(2200, "Not necessary"),
                                new CategoryReference(2221, "Not necessary"),
                                new CategoryReference(2339, "Not necessary")
                        )
                )
                .pagination(Pagination.from(0))
                .build();


        SearchResult result = searchEngine.search(query);

        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .is(new Condition<>(searchResult ->
                        searchResult.getTopicReferences().size() == 50,
                        "Check if result contains 50 topic references",
                        new Object())
                );
    }

    @Test
    void shouldReturnEmptyResultWhenSearchByQueryNotSuccessful() throws SearchException, InvalidPaginationException {
        SearchQuery query = SearchQuery.builder()
                .text("ijahsfijahskjfhakjshfkajhskfjhaksjhfkjahskfjhakjshfkajhskfjhaf")
                .categories(List.of())
                .pagination(Pagination.from(0))
                .build();


        SearchResult result = searchEngine.search(query);

        assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .is(new Condition<>(searchResult ->
                        searchResult.getTopicReferences().isEmpty(),
                        "Check if result contains 0 topic references",
                        new Object())
                );
    }
}
