package dev.silenzzz.rutracker4j;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.scrapper.exception.RuTracker4jException;
import dev.silenzzz.rutracker4j.scrapper.parse.PageParser;
import dev.silenzzz.rutracker4j.scrapper.net.AccountCredentials;
import dev.silenzzz.rutracker4j.scrapper.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.scrapper.search.SearchEngine;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchQuery;
import dev.silenzzz.rutracker4j.scrapper.search.model.SearchResult;
import lombok.NonNull;

import java.util.Collection;

public class DefaultRuTracker4jClient implements RuTracker4jClient {

    private final PageParser parser;
    private final SearchEngine search;

    public DefaultRuTracker4jClient(@NonNull AccountCredentials credentials) throws RuTracker4jException {
        JSoupHttpClient client = new JSoupHttpClient(credentials, null);
        this.parser = new PageParser(client);
        this.search = new SearchEngine(parser);
    }

    @Override
    public Topic findTopicById(long id) throws RuTracker4jException {
        return parser.findTopicById(id);
    }

    @Override
    public Attach findAttachById(long id) throws RuTracker4jException {
        return parser.findAttachById(id);
    }

    @Override
    public Torrent findTorrentById(long id) throws RuTracker4jException {
        return parser.findTorrentById(id);
    }

    @Override
    public Category findCategoryById(long id) throws RuTracker4jException {
        return parser.findCategoryById(id);
    }

    @Override
    public Collection<Category> getAllCategories() throws RuTracker4jException {
        return parser.getAllCategories();
    }

    @Override
    public SearchResult search(SearchQuery query) throws RuTracker4jException {
        return search.search(query);
    }
}
