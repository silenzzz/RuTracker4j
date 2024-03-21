package dev.silenzzz.rutracker4j.scrapper.exception;

import java.io.IOException;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTracker4jException extends IOException {

    public RuTracker4jException() {
        super();
    }

    public RuTracker4jException(String message) {
        super(message);
    }

    public RuTracker4jException(Throwable cause) {
        super(cause);
    }

    public RuTracker4jException(String message, Throwable cause) {
        super(message, cause);
    }
}
