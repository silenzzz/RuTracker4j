package dev.silenzzz.rutracker4j.exception;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerTopicNotFoundException extends RuTrackerException {

    public RuTrackerTopicNotFoundException() {
        super();
    }

    public RuTrackerTopicNotFoundException(String message) {
        super(message);
    }

    public RuTrackerTopicNotFoundException(Throwable cause) {
        super(cause);
    }

    public RuTrackerTopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
