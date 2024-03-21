package dev.silenzzz.rutracker4j;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchQuery;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchResult;

import java.util.Collection;

public interface RuTracker4jClient {

    /**
     * Find a topic by id.
     *
     * @param id Topic id
     * @return Topic
     */
    Topic findTopicById(long id) throws RuTracker4jException;

    /**
     * Find attach by id.
     *
     * @param id Attach id
     * @return Attach
     */
    Attach findAttachById(long id) throws RuTracker4jException;

    /**
     * Find torrent by id.
     *
     * @param id Torrent id
     * @return Torrent
     */
    Torrent findTorrentById(long id) throws RuTracker4jException;

    /**
     * Find category by id.
     *
     * @param id Category id
     * @return Category
     */
    Category findCategoryById(long id) throws RuTracker4jException;

    /**
     * Get all categories.
     *
     * @return Collection with categories
     */
    Collection<Category> getAllCategories() throws RuTracker4jException;

    /**
     * Search topics by query.
     *
     * @param query Category ids and text query
     * @return Search result with topic references.
     */
    SearchResult search(SearchQuery query) throws RuTracker4jException;

}
