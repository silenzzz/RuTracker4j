package dev.silenzzz.rutracker4j.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection.Method;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum URL {

    INDEX("https://rutracker.org/forum/index.php", Method.POST),
    LOGIN("https://rutracker.org/forum/login.php", Method.POST),
    TOPIC("https://rutracker.org/forum/viewtopic.php?t=%d"),
    CATEGORIES("https://rutracker.org/forum/tracker.php"),
    SEARCH("https://rutracker.org/forum/tracker.php"),
    TORRENT_DOWNLOAD_LINK("https://rutracker.org/forum/dl.php?t=%d"),
    ;

    private final String value;

    @SuppressWarnings("NonFinalFieldInEnum")
    private Method method = Method.GET;

    public String insertId(long id) {
        for (URL URL : URL.values()) {
            if (this == URL) {
                return String.format(URL.value, id);
            }
        }
        throw new RuntimeException(); // Unreachable NOSONAR
    }
}
