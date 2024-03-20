package dev.silenzzz.rutracker4j;

import dev.silenzzz.rutracker4j.domain.Attach;
import dev.silenzzz.rutracker4j.domain.Category;
import dev.silenzzz.rutracker4j.domain.Topic;
import dev.silenzzz.rutracker4j.domain.Torrent;
import dev.silenzzz.rutracker4j.exception.RuTrackerException;
import dev.silenzzz.rutracker4j.exception.RuTrackerParseException;
import dev.silenzzz.rutracker4j.extractor.PageDataExtractor;
import dev.silenzzz.rutracker4j.net.JSoupHttpClient;
import dev.silenzzz.rutracker4j.value.AccountCredentials;
import lombok.NonNull;

import java.util.Collection;

public class DefaultRuTracker4jClient implements RuTracker4jClient {

    private final PageDataExtractor extractor;

    public DefaultRuTracker4jClient(@NonNull AccountCredentials credentials) throws RuTrackerException {
        JSoupHttpClient client = new JSoupHttpClient(credentials, null);
        this.extractor = new PageDataExtractor(client);
    }

    @Override
    public Topic findTopicById(long id) throws RuTrackerException {
        return extractor.findTopicById(id);
    }

    @Override
    public Attach findAttachById(long id) throws RuTrackerException {
        return extractor.findAttachById(id);
    }

    @Override
    public Torrent findTorrentById(long id) throws RuTrackerException {
        return extractor.findTorrentById(id);
    }

    @Override
    public Category findCategoryById(long id) throws RuTrackerParseException {
        return extractor.findCategoryById(id);
    }

    @Override
    public Collection<Category> getAllCategories() throws RuTrackerParseException {
        return extractor.getAllCategories();
    }
}
