package dev.silenzzz.rutracker4j.extractor;

import dev.silenzzz.rutracker4j.constant.SizeType;
import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.exception.RuTrackerException;
import dev.silenzzz.rutracker4j.exception.RuTrackerParseException;
import dev.silenzzz.rutracker4j.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.value.AccountCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@EnabledIf(value = "ifEnvironmentVariablesSet")
class PageDataExtractorTest {

    private JSoupHttpClient client;

    private PageDataExtractor extractor;

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
                    extractor = new PageDataExtractor(client);
                }
        );
    }

    @Test
    void shouldReturnCategoryById() throws RuTrackerParseException {
        Category expected = categorySample();
        Category actual = extractor.findCategoryById(expected.getId());

        assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnTorrentById() throws RuTrackerException {
        Torrent expected = torrentSample();
        Torrent actual = extractor.findTorrentById(expected.getId());

        assertThat(actual)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnTopicById() throws RuTrackerException {
        long id = 6499423L;
        Topic topicById = extractor.findTopicById(id);
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
        assertThrows(RuTrackerException.class, () -> extractor.findAttachById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 123L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenTorrentNotFound(long id) {
        assertThrows(RuTrackerException.class, () -> extractor.findTorrentById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 123L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenTopicNotFound(long id) {
        assertThrows(RuTrackerException.class, () -> extractor.findTopicById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 12L, 1234L, 12345L, 123456L, 123123123L, 0L, -1L})
    void shouldThrowNotFoundExceptionWhenCategoryNotFound(long id) {
        assertThrows(RuTrackerException.class, () -> extractor.findCategoryById(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnTopicsByIds(long id) throws RuTrackerException {
        Topic topic = extractor.findTopicById(id);

        assertThat(topic)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnAttachesByIds(long id) throws RuTrackerException {
        Attach attach = extractor.findAttachById(id);

        assertThat(attach)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {6231554L, 6091099L, 6502335L})
    void shouldReturnTorrentsByIds(long id) throws RuTrackerException {
        Torrent torrent = extractor.findTorrentById(id);

        assertThat(torrent)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @ParameterizedTest
    @ValueSource(longs = {2093L, 2475L, 2527})
    void shouldReturnCategoriesByIds(long id) throws RuTrackerException {
        Category category = extractor.findCategoryById(id);

        assertThat(category)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldReturnAllCategories() throws RuTrackerParseException {
        Collection<Category> categories = extractor.getAllCategories();

        assertThat(categories)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1310);
    }

    public Category categorySample() {
        return category(9L, "Русские сериалы");
    }

    public Attach attachSample() {
        return attach(6499423L, 2.42f, SizeType.GB, torrentSample());
    }

    public Torrent torrentSample() {
        return torrent(6499423L,
                "https://rutracker.org/forum/dl.php?t=6499423",
                "magnet:?xt=urn:btih:E2A33CA1D2A5BB1304B98C5164A05094E88F2198&tr=http%3A%2F%2Fbt.t-ru.org%2Fann%3Fmagnet");
    }

    private Category category(long id, String name) {
        return Category.builder()
                .id(id)
                .name(name)
                .build();
    }

    private Torrent torrent(long id, String link, String magnetLink) {
        return Torrent.builder()
                .id(id)
                .link(link)
                .magnetLink(magnetLink)
                .build();
    }

    private Attach attach(long id, float size, SizeType type, Torrent torrent) {
        return Attach.builder()
                .id(id)
                .size(size)
                .type(type)
                .torrent(torrent)
                .build();
    }
}
