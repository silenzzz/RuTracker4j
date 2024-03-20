package dev.silenzzz.rutracker4j;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.exception.RuTrackerException;
import dev.silenzzz.rutracker4j.exception.RuTrackerParseException;

import java.util.Collection;

public interface RuTracker4jClient {

    /**
     * Find a topic by id.
     *
     * @param id
     * @return Topic
     */
    Topic findTopicById(long id) throws RuTrackerException;

    /**
     * Find attach by id.
     *
     * @param id
     * @return Attach
     */
    Attach findAttachById(long id) throws RuTrackerException;

    /**
     * Find torrent by id.
     *
     * @param id
     * @return Torrent
     */
    Torrent findTorrentById(long id) throws RuTrackerException;

    /**
     * Find category by id.
     *
     * @param id
     * @return Category
     */
    Category findCategoryById(long id) throws RuTrackerParseException;

    /**
     * Get all categories.
     *
     * @return Collection with categories
     */
    Collection<Category> getAllCategories() throws RuTrackerParseException;

}
