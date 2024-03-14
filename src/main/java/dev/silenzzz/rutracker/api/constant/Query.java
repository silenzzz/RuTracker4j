package dev.silenzzz.rutracker.api.constant;

import dev.silenzzz.rutracker.api.exception.RuTrackerParseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.select.Elements;

import java.util.Objects;
import java.util.Optional;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Getter
@RequiredArgsConstructor
public enum Query {

    LOGGED_USERNAME("a[id='logged-in-username']"),
    LOGIN_ERROR_MESSAGE("h4[class*='warnColor']"),
    CAPTCHA_IMAGE("img[alt='pic']"),
    CAPTCHA_INPUT("input[class='reg-input']"),
    CATEGORY("a[href^='viewforum.php?f=']"),
    TORRENT_FILE_DOWNLOAD_LINK("a[href^='dl.php?t=']"),
    FILE_SIZE("#tor-size-humn"),
    ;

    private final String value;

    public String getSelectedElementsValue(Elements elements) throws RuTrackerParseException {
        try {
            // @formatter:off
            return Optional.of(Objects.requireNonNull(
                    elements.select(this.value)
                            .first())
                            .text()
                    )
                    .orElseThrow(RuTrackerParseException::new);
            // @formatter:on
        } catch (NullPointerException e) {
            throw new RuTrackerParseException(e);
        }
    }
}
