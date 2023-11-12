package dev.demmage.rutracker.api;

import dev.demmage.rutracker.api.domain.*;

import java.util.List;

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

    /**
     * Get all rutracker.org categories
     * @return List with categories
     */
    List<Category> getAllCategories();

    Category findCategoryById(long id);

    List<Topic> searchTopics(SearchCondition searchCondition);

}
