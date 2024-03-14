package dev.silenzzz.rutracker.api;

import dev.silenzzz.rutracker.api.domain.*;
import dev.silenzzz.rutracker.api.domain.Category;
import dev.silenzzz.rutracker.api.domain.Topic;
import dev.silenzzz.rutracker.api.domain.Torrent;
import dev.silenzzz.rutracker.api.domain.User;
import dev.silenzzz.rutracker.api.model.SearchConditions;
import dev.silenzzz.rutracker.api.model.SearchResult;

import java.util.List;

public interface RuTrackerClient {

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

    SearchResult search(SearchConditions searchConditions);

}
