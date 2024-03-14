package dev.silenzzz.rutracker.api.extractor;

import dev.silenzzz.rutracker.api.constant.SizeType;
import dev.silenzzz.rutracker.api.domain.Category;
import dev.silenzzz.rutracker.api.domain.Topic;
import dev.silenzzz.rutracker.api.domain.Torrent;
import dev.silenzzz.rutracker.api.exception.RuTrackerException;
import dev.silenzzz.rutracker.api.net.JSoupHttpClient;
import dev.silenzzz.rutracker.api.value.AccountCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
class PageDataExtractorTest {

    private JSoupHttpClient client;

    private PageDataExtractor extractor;

    @BeforeEach
    @Test
    void shouldInitializeExtractor() {
        assertDoesNotThrow(() -> {
                    client = new JSoupHttpClient(
                            new AccountCredentials(
                                    System.getProperty("username"),
                                    System.getProperty("password")
                            ),
                            null
                    );
                    extractor = new PageDataExtractor(client);
                }
        );
    }

    @Test
    void shouldReturnTopicById() throws RuTrackerException {
        Topic topicById = extractor.findTopicById(6499423L);

        assertThat(topicById)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 6499423L)
                .hasFieldOrPropertyWithValue("title", "Шаманка / Серии: 1-4 из 4 (Наталья Титова) [2024, мелодрама, WEBRip]")
                .hasFieldOrPropertyWithValue("category", new Category(9L, null, "Русские сериалы"))
                .hasFieldOrPropertyWithValue("torrent", new Torrent(6499423L, "https://rutracker.org/forum/dl.php?t=6499423", null, 2.42f, SizeType.GB));
    }
}
