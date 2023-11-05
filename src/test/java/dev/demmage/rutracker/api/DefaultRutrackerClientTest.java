package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.domain.Topic;
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
    //@Timeout(5)
    void shouldReturnTopic() {
        Topic topicById = rutrackerClient.findTopicById(6358253);

        assertNotNull(topicById);

        assertEquals("Guiluerme Macedo", topicById.getPosts().get(0).getUser().getNickname());
        assertEquals(49322417, topicById.getPosts().get(1).getUser().getId());

        assertEquals("djdisco75", topicById.getPosts().get(1).getUser().getNickname());


        assertEquals("alsevas", topicById.getPosts().get(2).getUser().getNickname());


        assertEquals("Igorek895", topicById.getPosts().get(3).getUser().getNickname());


        assertEquals("alsevas", topicById.getPosts().get(4).getUser().getNickname());
    }

    @Test
    void shouldThrowLoginExceptionWhenWrongCredentialsGiven() {
        assertThrows(CredentialException.class, () -> new DefaultRutrackerClient(new AccountCredentials("123", "123")));
    }

    @Test
    @AfterEach
    void cleanup() {

    }
}