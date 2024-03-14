package dev.silenzzz.rutracker.api.net;

import dev.silenzzz.rutracker.api.exception.RuTrackerException;
import dev.silenzzz.rutracker.api.value.AccountCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Timeout(30)
class JSoupHttpClientTest {

    private JSoupHttpClient client;

    @BeforeEach
    @Test
    void shouldInitializeClient() {
        assertDoesNotThrow(() ->
                client = new JSoupHttpClient(
                        new AccountCredentials(
                                System.getProperty("username"),
                                System.getProperty("password")
                        ),
                        null // TODO NOSONAR
                )
        );
    }

    @Test
    void shouldRefreshAndReturnCookies() throws RuTrackerException {
        assertThat(client.refreshCookies())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsKeys("bb_ssl", "bb_session");
    }

    @Test
    void shouldReturnCookiesAfterSuccessAuthentication() {
        assertThat(client.getCookies())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsKeys("bb_ssl", "bb_session");
    }
}
