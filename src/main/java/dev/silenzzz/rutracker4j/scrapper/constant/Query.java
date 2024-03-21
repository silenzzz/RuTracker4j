package dev.silenzzz.rutracker4j.scrapper.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@SuppressWarnings("SpellCheckingInspection")
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
    TOPIC_ROW("tr[class=\"tCenter hl-tr\"]"),
    TOPIC_CATEGORY_ROW("a[class=\"gen f ts-text\"]"),
    TOPIC_TITLE_ROW("a[class=\"med tLink tt-text ts-text hl-tags bold tags-initialized\"]"),
    ;

    private final String value;

}
