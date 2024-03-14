package dev.silenzzz.rutracker.api.model;

import dev.silenzzz.rutracker.api.ref.AuthorReference;
import dev.silenzzz.rutracker.api.ref.TopicReference;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Map;

@Value
@AllArgsConstructor
public class SearchResult {

    Map<AuthorReference, TopicReference> results;

}
