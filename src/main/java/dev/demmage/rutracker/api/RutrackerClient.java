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

    User findUserById(long id);

    Torrent findTorrentFileById(long id);

}
