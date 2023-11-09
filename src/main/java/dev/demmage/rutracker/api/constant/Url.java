package dev.demmage.rutracker.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Url {

    LOGIN_URL("https://rutracker.org/forum/login.php"),
    TOPIC_URL("https://rutracker.org/forum/viewtopic.php?t=%d"),
    TOREENT_DOWNLOAD_URL("https://rutracker.org/forum/dl.php?t=%d"),

    //// TODO: 09.11.2023
    TOREENT_DOWNLOAD_MAGNET_URL("https://rutracker.org/forum/dl.php?t=%d"),

    USER_URL("https://rutracker.org/forum/profile.php?mode=viewprofile&u=%d"),;

    final String value;

    public String insertId(long id) {
        for (Url url : Url.values()) {
            if (this == url) {
                return String.format(url.value, id);
            }
        }
        // STUB
        throw new RuntimeException(); //NOSONAR
    }
}