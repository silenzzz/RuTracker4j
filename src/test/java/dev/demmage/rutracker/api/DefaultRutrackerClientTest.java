package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.constant.SizeType;
import dev.demmage.rutracker.api.domain.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;

import javax.security.auth.login.CredentialException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(30)
class DefaultRutrackerClientTest {

    private static RutrackerClient rutrackerClient;

    private final SimpleDateFormat profileDateFormatter = new SimpleDateFormat("yyyy-LL-dd", new Locale("ru", "RU"));

    private static final long TOPIC_ID = 6358253;

    private static Topic topic;

    @BeforeAll
    static void setup() {
        rutrackerClient = new DefaultRutrackerClient(new AccountCredentials(System.getProperty("username"), System.getProperty("password")));
        topic = rutrackerClient.findTopicById(TOPIC_ID);
    }

    @Test
    void shouldReturnTopic() {
        assertNotNull(topic);
        assertEquals(topic.getTitle(), "[X-Plane 11] [X11] - Aerobask Phenom 300 [ENG]");
        assertEquals(new Category("Самолёты и вертолёты для X-Plane"), topic.getCategory());
        assertThat(topic.getSuperCategories())
                .isNotNull()
                .hasSameElementsAs(List.of(new Category("Прочее для Microsoft Flight Simulator, Prepar3D, X-Plane"),
                        new Category("Игры"), new Category("Самолёты и вертолёты для X-Plane")));
        assertFalse(topic.getPosts().isEmpty());
    }

    @Test
    @SneakyThrows
    void shouldReturnTorrentFromTopic() {
        assertEquals(Torrent.builder()
                .id(TOPIC_ID)
                .link("https://rutracker.org/forum/dl.php?t=6358253")
                .magnetLink("magnet:?xt=urn:btih:044419ACCC05D780A2A3C91FAEE7AD6373D1F626&tr=http%3A%2F%2Fbt3.t-ru.org%2Fann%3Fmagnet")
                .filesSize(1.24F)
                .fileSizeType(SizeType.GB)
                .build(), topic.getTorrent());
    }

    @Test
    void shouldReturnTorrentFileByID() {
        assertEquals(Torrent.builder()
                .id(TOPIC_ID)
                .link("https://rutracker.org/forum/dl.php?t=6358253")
                .build(), rutrackerClient.findTorrentFileById(TOPIC_ID));
    }

    @Test
    void shouldReturnCategoryFromTopic() {
        assertEquals(Category.builder()
                .id(2012)
                .name("Самолёты и вертолёты для X-Plane")
                .build(), topic.getCategory());
    }

    @Test
    void shouldReturnPostsFromTopic() {
        topic.getPosts().forEach(Assertions::assertNotNull);
        assertEquals(9, topic.getPosts().size());
    }

    @Test
    @SneakyThrows
    void shouldReturnAllUsersInTopic() {
        Post post1 = topic.getPosts().get(0);
        User user1 = post1.getUser();
        assertEquals("Guiluerme Macedo", user1.getNickname());
        assertEquals(49322417, user1.getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"), user1.getCountry());

        Post post2 = topic.getPosts().get(1);
        assertEquals("djdisco75", post2.getUser().getNickname());
        assertEquals(26642586, post2.getUser().getId());
        assertEquals(new Country("Россия", "https://static.rutracker.cc/flags/143.gif"), post2.getUser().getCountry());

        Post post3 = topic.getPosts().get(2);
        assertEquals("alsevas", post3.getUser().getNickname());
        assertEquals(8117637, post3.getUser().getId());
        // FIXME: 05.11.2023
        //assertEquals(new Country("СССР", "https://static.rutracker.cc/flags/182.gif"),post2.getUser().getCountry());

        Post post4 = topic.getPosts().get(3);
        assertEquals("Igorek895", post4.getUser().getNickname());
        assertEquals(36911247, post4.getUser().getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"), post1.getUser().getCountry());

        Post post5 = topic.getPosts().get(4);
        assertEquals("alsevas", post5.getUser().getNickname());
        assertEquals(8117637, post5.getUser().getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"), post1.getUser().getCountry());
    }

    @Test
    void shouldReturnNullThenUserHasNoCountry() {
        assertNull(topic.getPosts().get(7).getUser().getCountry().getName());
    }

    @Test
    @SneakyThrows
    void shouldReturnUserById() {
        User userById = rutrackerClient.findUserById(8117637);

        assertNotNull(userById);
        assertEquals("alsevas", userById.getNickname());
        assertEquals(8117637, userById.getId());
        assertEquals(new Country("СССР", "https://static.rutracker.cc/flags/182.gif"), userById.getCountry());
        assertEquals(28, userById.getMessagesCount());
        assertEquals(profileDateFormatter.parse("2009-01-21"), userById.getRegistered());
        assertEquals(profileDateFormatter.parse("2023-11-10"), userById.getLastVisit());
        assertEquals("14 лет 9 месяцев", userById.getSeniority());
    }

    @Test
    @Disabled
        // FIXME: 05.11.2023
    void shouldThrowLoginExceptionWhenWrongCredentialsGiven() {
        assertThrows(CredentialException.class, () -> {
            RutrackerClient defaultRutrackerClient = new DefaultRutrackerClient(new AccountCredentials("123", "123"));
        });
    }

    @Test
    void shouldReturnAllCategories() {
        List<Category> categories = rutrackerClient.getAllCategories();
        assertThat(categories).hasSize(1190);
    }

    @Test
    @AfterEach
    void cleanup() {

    }
}