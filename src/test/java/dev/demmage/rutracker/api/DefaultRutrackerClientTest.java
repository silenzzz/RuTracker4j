package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.domain.*;
import org.junit.jupiter.api.*;

import javax.security.auth.login.CredentialException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRutrackerClientTest {

    private RutrackerClient rutrackerClient;

    @Test
    @BeforeEach
    void shouldCreateDefaultApi() {
        rutrackerClient = new DefaultRutrackerClient(new AccountCredentials(System.getProperty("username"), System.getProperty("password")));
    }

    @Test
    @Timeout(30)
    void shouldReturnTopic() {
        final long id = 6358253;
        Topic topicById = rutrackerClient.findTopicById(id);

        assertNotNull(topicById);
        assertEquals(Category.builder()
                .id(2012)
                .name("Самолёты и вертолёты для X-Plane")
                .build(), topicById.getCategory());

        assertEquals(Torrent.builder()
                .id(id)
                .link("https://rutracker.org/forum/dl.php?t=6358253")
                .build(), topicById.getTorrent());

        Post post1 = topicById.getPosts().get(0);
        assertEquals("Guiluerme Macedo", post1.getUser().getNickname());
        assertEquals(49322417, post1.getUser().getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"),post1.getUser().getCountry());

        Post post2 = topicById.getPosts().get(1);
        assertEquals("djdisco75", post2.getUser().getNickname());
        assertEquals(26642586, post2.getUser().getId());
        assertEquals(new Country("Россия", "https://static.rutracker.cc/flags/143.gif"),post2.getUser().getCountry());

        Post post3 = topicById.getPosts().get(2);
        assertEquals("alsevas", post3.getUser().getNickname());
        assertEquals(8117637, post3.getUser().getId());
        // FIXME: 05.11.2023 
        //assertEquals(new Country("СССР", "https://static.rutracker.cc/flags/182.gif"),post2.getUser().getCountry());


        Post post4 = topicById.getPosts().get(3);
        assertEquals("Igorek895", post4.getUser().getNickname());
        assertEquals(36911247, post4.getUser().getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"),post1.getUser().getCountry());

        Post post5 = topicById.getPosts().get(4);
        assertEquals("alsevas", post5.getUser().getNickname());
        assertEquals(8117637, post5.getUser().getId());
        assertEquals(new Country("RuTracker.org", "https://static.rutracker.cc/flags/199.gif"),post1.getUser().getCountry());

        assertNull(topicById.getPosts().get(7).getUser().getCountry().getName());
    }

    @Test
    @Disabled
        // FIXME: 05.11.2023
    void shouldThrowLoginExceptionWhenWrongCredentialsGiven() {
        assertThrows(CredentialException.class, () -> new DefaultRutrackerClient(new AccountCredentials("123", "123")));
    }

    @Test
    @AfterEach
    void cleanup() {

    }
}