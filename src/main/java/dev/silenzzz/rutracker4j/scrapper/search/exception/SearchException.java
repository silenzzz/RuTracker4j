package dev.silenzzz.rutracker4j.scrapper.search.exception;

import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class SearchException extends RuTracker4jException {

    public SearchException() {
        super();
    }

    public SearchException(String message) {
        super(message);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
