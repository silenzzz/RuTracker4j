package dev.silenzzz.rutracker4j;

import dev.silenzzz.rutracker4j.constant.SizeType;
import dev.silenzzz.rutracker4j.exception.RuTrackerException;
import dev.silenzzz.rutracker4j.value.AccountCredentials;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import javax.security.auth.login.CredentialException;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(30)
@Disabled
class DefaultRuTracker4jClientTest {

    private static RuTracker4jClient rutrackerClient;

    private final SimpleDateFormat profileDateFormatter = new SimpleDateFormat("yyyy-LL-dd", new Locale("ru", "RU"));

    private static final long TOPIC_ID = 6358253;

    private static Topic topic;

    @BeforeAll
    static void setup() throws RuTrackerException {
        //rutrackerClient = new DefaultRuTrackerClient(new AccountCredentials(System.getProperty("username"), System.getProperty("password")));
        topic = rutrackerClient.findTopicById(TOPIC_ID);
    }

    @Test
    void shouldReturnTopic() {
        assertNotNull(topic);
        assertEquals(topic.getTitle(), "[X-Plane 11] [X11] - Aerobask Phenom 300 [ENG]");
        //Assertions.assertEquals(Category.builder().name("Самолёты и вертолёты для X-Plane").build(), topic.getCategory());
    }

    @Test
    @SneakyThrows
    void shouldReturnTorrentFromTopic() {
        Assertions.assertEquals(Torrent.builder()
                .id(TOPIC_ID)
                .link("https://rutracker.org/forum/dl.php?t=6358253")
                .magnetLink("magnet:?xt=urn:btih:044419ACCC05D780A2A3C91FAEE7AD6373D1F626&tr=http%3A%2F%2Fbt3.t-ru.org%2Fann%3Fmagnet")
                //.filesSize(1.24F)
                //.fileSizeType(SizeType.GB)
                .build(), topic.getAttach());
    }

    @Test
    void shouldReturnTorrentFileByID() {
//        Assertions.assertEquals(Torrent.builder()
//                .id(TOPIC_ID)
//                .link("https://rutracker.org/forum/dl.php?t=6358253")
//                .build(), rutrackerClient.findAttachById(TOPIC_ID));
    }

    @Test
    void shouldReturnCategoryFromTopic() {
//        assertEquals(Category.builder()
//                .id(2012)
//                .name("Самолёты и вертолёты для X-Plane")
//                .build(), topic.getCategory());
    }

    @Test
    @SneakyThrows
    void shouldReturnUserById() {
//        User userById = rutrackerClient.findUserById(8117637);
//
//        assertNotNull(userById);
//        assertEquals("alsevas", userById.getNickname());
//        assertEquals(8117637, userById.getId());
//        Assertions.assertEquals(new Country("СССР", "https://static.rutracker.cc/flags/182.gif"), userById.getCountry());
//        assertEquals(28, userById.getMessagesCount());
//        assertEquals(profileDateFormatter.parse("2009-01-21"), userById.getRegistered());
//        //assertEquals(profileDateFormatter.parse("2023-11-10"), userById.getLastVisit());
//        assertEquals("14 лет 9 месяцев", userById.getSeniority());
    }

    @Test
    @Disabled
        // FIXME: 05.11.2023
    void shouldThrowLoginExceptionWhenWrongCredentialsGiven() {
        assertThrows(CredentialException.class, () -> {
            RuTracker4jClient defaultRutrackerClient = new DefaultRuTracker4jClient(new AccountCredentials("123", "123"));
        });
    }

    @Test
    void shouldReturnAllCategories() {
//        Collection categories = rutrackerClient.getAllCategories();
//
//        assertThat(categories)
//                .hasSize(1309)
//                .contains(Category.builder()
//                        .id(1705)
//                        .name("Progressive & Art-Rock (lossy)")
//                        .parentName("Рок-музыка")
//                        .build());
    }

    @Test
    void shouldReturnTopicWithFilmWhenQueryAndCategoryAreFilled() {
        List<Category> categories = List.of(
                Category.builder()
                        .id(941L)
                        .build()
        );

//        SearchConditions conditions = SearchConditions.builder()
//                .query("Вот и мы")
//                .categories(categories)
//                .build();

        //rutrackerClient.search(conditions);

        //List<Topic> topics = rutrackerClient.search(conditions);

//        assertThat(topics)
//                .isNotNull()
//                .hasSize(1);
    }

    @Test
    @AfterEach
    void cleanup() {

    }
}
