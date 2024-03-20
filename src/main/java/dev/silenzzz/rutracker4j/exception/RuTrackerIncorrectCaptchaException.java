package dev.silenzzz.rutracker4j.exception;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class RuTrackerIncorrectCaptchaException extends RuTrackerException {

    public RuTrackerIncorrectCaptchaException(String message) {
        super(message);
    }

    public RuTrackerIncorrectCaptchaException(Throwable cause) {
        super(cause);
    }

    public RuTrackerIncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
