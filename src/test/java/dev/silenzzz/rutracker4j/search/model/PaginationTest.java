package dev.silenzzz.rutracker4j.search.model;

import dev.silenzzz.rutracker4j.scrapper.search.exception.InvalidPaginationException;
import dev.silenzzz.rutracker4j.scrapper.search.model.Pagination;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
class PaginationTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void shouldEvaluateCorrectIndexesFromPageIndex(int page) throws InvalidPaginationException {
        Pagination pagination = Pagination.ofPage(page);

        assertThat(pagination)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("startIndex", page * 50 - 50)
                .hasFieldOrPropertyWithValue("endIndex", page * 50);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 50, 200, 350})
    void shouldEvaluateCorrectIndexesFromIndex(int index) throws InvalidPaginationException {
        Pagination pagination = Pagination.from(index);

        assertThat(pagination)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("startIndex", index)
                .hasFieldOrPropertyWithValue("endIndex", index + 50);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 50, 200, 350})
    void shouldEvaluateCorrectIndexesFromRange(int index) throws InvalidPaginationException {
        Pagination pagination = Pagination.ofRange(index, index + 50);

        assertThat(pagination)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("startIndex", index)
                .hasFieldOrPropertyWithValue("endIndex", index + 50);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 11, 55, 1337})
    void shouldThrowExceptionWithIncorrectPageIndex(int index) {
        assertThrows(InvalidPaginationException.class, () -> Pagination.ofPage(index));
    }

    @ParameterizedTest
    @CsvSource({
            "0,0",
            "50,50",
            "-1,50",
            "50,-1",
            "-1,-1",
            "500,500",
            "50,550",
            "500,450",
            "0,49",
            "33,550",
            "50,333",
            "333,333",
            "800,50",
            "50,800",
    })
    void shouldThrowExceptionWithIncorrectIndexRange(int startIndex, int endIndex) {
        assertThrows(InvalidPaginationException.class, () -> Pagination.ofRange(startIndex, endIndex));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11, 22, 60, 500, 550, 1337})
    void shouldThrowExceptionWithIncorrectStartIndex(int startIndex) {
        assertThrows(InvalidPaginationException.class, () -> Pagination.from(startIndex));
    }
}
