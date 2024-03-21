package dev.silenzzz.rutracker4j.scrapper.search.model;

import dev.silenzzz.rutracker4j.scrapper.search.ref.TopicReference;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
public class SearchResult {

    @Getter
    private final List<TopicReference> topicReferences = new ArrayList<>();

    public void addTopicReference(TopicReference topicReference) {
        topicReferences.add(topicReference);
    }
}
