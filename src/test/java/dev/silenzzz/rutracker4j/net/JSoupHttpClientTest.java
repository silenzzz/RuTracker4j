package dev.silenzzz.rutracker4j.net;

import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.net.AccountCredentials;
import dev.silenzzz.rutracker4j.scrapper.net.JSoupHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Timeout(30)
@EnabledIf(value = "ifEnvironmentVariablesSet")
class JSoupHttpClientTest {

    private JSoupHttpClient client;

    private static boolean ifEnvironmentVariablesSet() {
        return System.getenv("USERNAME") != null && System.getenv("PASSWORD") != null;
    }

    @BeforeEach
    @Test
    void shouldInitializeClient() {
        assertDoesNotThrow(() ->
                client = new JSoupHttpClient(
                        new AccountCredentials(
                                System.getenv("USERNAME"),
                                System.getenv("PASSWORD")
                        ),
                        null // TODO NOSONAR
                )
        );
    }

    @Test
    void shouldRefreshAndReturnCookies() throws RuTracker4jException {
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
