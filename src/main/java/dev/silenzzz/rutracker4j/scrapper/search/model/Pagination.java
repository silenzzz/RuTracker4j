package dev.silenzzz.rutracker4j.scrapper.search.model;

import dev.silenzzz.rutracker4j.scrapper.search.exception.InvalidPaginationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.checkerframework.common.value.qual.IntRange;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Pagination {

    private int startIndex;
    private int endIndex;

    private static final int STEP = 50;

    public static Pagination ofPage(@IntRange(from = 1, to = 10) int page) throws InvalidPaginationException {
        if (page == 1) {
            return Pagination.builder()
                    .startIndex(0)
                    .endIndex(50)
                    .build();
        }

        int start = page * STEP - STEP;
        int end = page * STEP;
        validateIndexes(start, end);

        return Pagination.builder()
                .startIndex(start)
                .endIndex(end)
                .build();
    }

    public static Pagination ofRange(@IntRange(from = 0, to = 450) int startIndex, @IntRange(from = 50, to = 500) int endIndex) throws InvalidPaginationException {
        validateIndexes(startIndex, endIndex);

        return Pagination.builder()
                .startIndex(startIndex)
                .endIndex(endIndex)
                .build();
    }

    public static Pagination from(@IntRange(from = 0, to = 500) int startIndex) throws InvalidPaginationException {
        int end = startIndex + STEP;
        validateIndexes(startIndex, end);

        return Pagination.builder()
                .startIndex(startIndex)
                .endIndex(end)
                .build();
    }

    private static void validateIndexes(int startIndex, int endIndex) throws InvalidPaginationException {
        if (startIndex == endIndex) {
            throwException(startIndex, endIndex);
        }

        if (startIndex >= endIndex) {
            throwException(startIndex, endIndex);
        }

        if (startIndex < 0 || startIndex > 450) {
            throwException(startIndex, endIndex);
        }

        if (endIndex < 50 || endIndex > 500) {
            throwException(startIndex, endIndex);
        }

        if (startIndex % 50 != 0 || endIndex % 50 != 0) {
            throwException(startIndex, endIndex);
        }
    }

    private static void throwException(int startIndex, int endIndex) throws InvalidPaginationException {
        throw new InvalidPaginationException(String.format("Invalid pagination with start index=%d, end index=%d", startIndex, endIndex));
    }
}
