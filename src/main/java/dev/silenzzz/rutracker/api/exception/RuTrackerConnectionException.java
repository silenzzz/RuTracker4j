package dev.silenzzz.rutracker.api.exception;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerConnectionException extends RuTrackerException {

    public RuTrackerConnectionException() {
        super();
    }

    public RuTrackerConnectionException(String message) {
        super(message);
    }

    public RuTrackerConnectionException(Throwable cause) {
        super(cause);
    }

    public RuTrackerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
