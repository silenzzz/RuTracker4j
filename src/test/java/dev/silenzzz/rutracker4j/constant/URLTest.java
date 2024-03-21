package dev.silenzzz.rutracker4j.constant;

import dev.silenzzz.rutracker4j.scrapper.constant.URL;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLTest {

    @Test
    void shouldInsertId() {
        final String expected = "https://rutracker.org/forum/viewtopic.php?t=1";
        final String actual = URL.TOPIC.insertId(1);

        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .isNotBlank()
                .isEqualTo(expected);
    }
}
