package dev.silenzzz.rutracker4j.exception;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerParseException extends RuTrackerException {

    public RuTrackerParseException() {
        super();
    }

    public RuTrackerParseException(String message) {
        super(message);
    }

    public RuTrackerParseException(Throwable cause) {
        super(cause);
    }

    public RuTrackerParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
