package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.domain.Topic;
import dev.demmage.rutracker.api.domain.Torrent;
import dev.demmage.rutracker.api.domain.User;

public interface RutrackerClient {

    /**
     * Find a topic by id
     * @param id
     * @return Topic
     */
    Topic findTopicById(long id);

    /**
     * Find user by id
     * @param id
     * @return User
     */
    User findUserById(long id);

    /**
     * Find torrent file by id
     * @param id
     * @return Torrent
     */
    Torrent findTorrentFileById(long id);

}
