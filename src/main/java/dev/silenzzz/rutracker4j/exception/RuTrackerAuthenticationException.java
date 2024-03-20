package dev.silenzzz.rutracker4j.exception;


/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerAuthenticationException extends RuTrackerException {

    public RuTrackerAuthenticationException() {
        super();
    }

    public RuTrackerAuthenticationException(String message) {
        super(message);
    }

    public RuTrackerAuthenticationException(Throwable cause) {
        super(cause);
    }

    public RuTrackerAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
