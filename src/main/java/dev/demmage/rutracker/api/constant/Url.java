package dev.demmage.rutracker.api.constant;

import lombok.Getter;

@Getter
public enum Url {

    LOGIN_URL("https://rutracker.org/forum/login.php"),
    TOPIC_URL("https://rutracker.org/forum/viewtopic.php?t=%d"),
    USER_URL("https://rutracker.org/forum/profile.php?mode=viewprofile&u=%d");

    final String value;

    Url(String value) {
        this.value = value;
    }
}
