package dev.silenzzz.rutracker4j.constant;

import dev.silenzzz.rutracker4j.exception.RuTrackerParseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
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

    LOGGED_USERNAME("a[id=\"logged-in-username\"]"),
    LOGIN_ERROR("h4[class*=\"warnColor\"]"),
    TOPIC_TITLE("a[id=\"topic-title\"]"),
    TOPIC_NOT_FOUND("div[class=\"mrg_16\"]"),
    CAPTCHA_IMAGE("img[alt=\"pic\"]"),
    CAPTCHA_INPUT("input[class=\"reg-input\"]"),
    CATEGORY("td[class^=\"nav t-breadcrumb-top\"] > a[href^=\"viewforum.php?f=\"]"),
    CATEGORY_OPTION("optgroup > option[id^=\"fs-\"]"),
    TORRENT_FILE_DOWNLOAD_LINK("a[href^=\"dl.php?t=\"]"),
    TORRENT_FILE_DOWNLOAD_MAGNET_LINK("a[class=\"med magnet-link\"]"),
    FILE_SIZE("span[id=\"tor-size-humn\"]"),
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

    public String getSelectedElementsValue(Document document) throws RuTrackerParseException {
        return getSelectedElementsValue(document.select(this.getValue()));
    }
}
