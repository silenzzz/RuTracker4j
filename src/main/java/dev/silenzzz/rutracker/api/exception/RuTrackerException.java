package dev.silenzzz.rutracker.api.exception;

import java.io.IOException;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerException extends IOException {

    public RuTrackerException() {
        super();
    }

    public RuTrackerException(String message) {
        super(message);
    }

    public RuTrackerException(Throwable cause) {
        super(cause);
    }

    public RuTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}
