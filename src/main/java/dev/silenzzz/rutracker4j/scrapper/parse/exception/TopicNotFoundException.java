package dev.silenzzz.rutracker4j.scrapper.parse.exception;

import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class TopicNotFoundException extends RuTracker4jException {

    public TopicNotFoundException() {
        super();
    }

    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException(Throwable cause) {
        super(cause);
    }

    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
