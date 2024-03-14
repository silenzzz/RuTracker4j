package dev.silenzzz.rutracker.api.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageURLTest {

    @Test
    void shouldInsertId() {
        final String expected = "https://rutracker.org/forum/viewtopic.php?t=1";
        final String actual = PageURL.TOPIC.insertId(1);

        assertEquals(expected, actual);
    }
}
