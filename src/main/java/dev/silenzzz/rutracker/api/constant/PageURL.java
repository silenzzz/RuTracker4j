package dev.silenzzz.rutracker.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection.Method;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum PageURL {

    // TODO: Use URL from java.net instead of string NOSONAR

    INDEX("https://rutracker.org/forum/index.php", Method.POST),
    LOGIN("https://rutracker.org/forum/login.php", Method.POST),
    TOPIC("https://rutracker.org/forum/viewtopic.php?t=%d"),
    CATEGORIES("https://rutracker.org/forum/tracker.php"),
    SEARCH("https://rutracker.org/forum/tracker.php"),

    TORRENT_DOWNLOAD("https://rutracker.org/forum/dl.php?t=%d"),

    // TODO: 09.11.2023 NOSONAR
    TORRENT_DOWNLOAD_MAGNET("https://rutracker.org/forum/dl.php?t=%d"),

    PROFILE("https://rutracker.org/forum/profile.php?mode=viewprofile&u=%d"),
    ;

    private final String value;

    @SuppressWarnings("NonFinalFieldInEnum")
    private Method method = Method.GET;

    public String insertId(long id) {
        for (PageURL pageURL : PageURL.values()) {
            if (this == pageURL) {
                return String.format(pageURL.value, id);
            }
        }
        throw new RuntimeException(); // Unreachable NOSONAR
    }
}
