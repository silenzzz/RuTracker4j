package dev.silenzzz.rutracker4j.extractor;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.domain.constant.SizeType;
import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.net.AccountCredentials;
import dev.silenzzz.rutracker4j.scrapper.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.scrapper.parse.PageParser;
import dev.silenzzz.rutracker4j.scrapper.parse.exception.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Timeout(30)
@EnabledIf(value = "ifEnvironmentVariablesSet")
class PageParserTest {

    private JSoupHttpClient client;
    private PageParser parser;

    private static boolean ifEnvironmentVariablesSet() {
        return System.getenv("USERNAME") != null && System.getenv("PASSWORD") != null;
    }

    @BeforeEach
    @Test
    void shouldInitializeExtractor() {
        assertDoesNotThrow(() -> {
                    client = new JSoupHttpClient(
                            new AccountCredentials(
                                    System.getenv("USERNAME"),
                                    System.getenv("PASSWORD")
                            ),
                            null
                    );
                    parser = new PageParser(client);
                }
        );
    }

    @Test
    void shouldReturnCategoryById() throws ParseException {
        Category expected = categorySample();
        Category actual = parser.findCategoryById(expected.getId());

        assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnTorrentById() throws RuTracker4jException {
        Torrent expected = torrentSample();
        Torrent actual = parser.findTorrentById(expected.getId());

        assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnTopicById() throws RuTracker4jException {
        long id = 6499423L;
        Topic topicById = parser.findTopicById(id);
        assertThat(topicById)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", "Шаманка / Серии: 1-4 из 4 (Наталья Титова) [2024, мелодрама, WEBRip]")
                .hasFieldOrPropertyWithValue("categories", List.of(categorySample()))
                .hasFieldOrPropertyWithValue("attach", attachSample());
    }

    
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 123L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenAttachNotFound(long id) {
        assertThrows(RuTracker4jException.class, () -> parser.findAttachById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 123L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenTorrentNotFound(long id) {
        assertThrows(RuTracker4jException.class, () -> parser.findTorrentById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 123L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenTopicNotFound(long id) {
        assertThrows(RuTracker4jException.class, () -> parser.findTopicById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenCategoryNotFound(long id) {
        assertThrows(RuTracker4jException.class, () -> parser.findCategoryById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnTopicsByIds(long id) throws RuTracker4jException {
        Topic topic = parser.findTopicById(id);

        assertThat(topic)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnAttachesByIds(long id) throws RuTracker4jException {
        Attach attach = parser.findAttachById(id);

        assertThat(attach)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnTorrentsByIds(long id) throws RuTracker4jException {
        Torrent torrent = parser.findTorrentById(id);

        assertThat(torrent)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {2093L, 2475L, 2527})
    void shouldReturnCategoriesByIds(long id) throws RuTracker4jException {
        Category category = parser.findCategoryById(id);

        assertThat(category)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }
    
    @Test
    void shouldReturnAllCategories() throws ParseException {
        Collection<Category> categories = parser.getAllCategories();

        assertThat(categories)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1310);
    }

    public Category categorySample() {
        return category();
    }

    public Attach attachSample() {
        return attach();
    }

    public Torrent torrentSample() {
        return torrent();
    }

    private Category category() {
        return Category.builder()
                .id(9L)
                .name("Русские сериалы")
                .build();
    }

    private Torrent torrent() {
        return Torrent.builder()
                .id(6499423L)
                .link("https://rutracker.org/forum/dl.php?t=6499423")
                .magnetLink("magnet:?xt=urn:btih:E2A33CA1D2A5BB1304B98C5164A05094E88F2198&tr=http%3A%2F%2Fbt.t-ru.org%2Fann%3Fmagnet")
                .build();
    }

    private Attach attach() {
        return Attach.builder()
                .id(6499423L)
                .size((float) 2.42)
                .type(SizeType.GB)
                .torrent(torrent())
                .build();
    }
}
