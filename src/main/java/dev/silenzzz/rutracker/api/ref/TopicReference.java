package dev.silenzzz.rutracker.api.ref;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TopicReference {

    long id;
    String name;

}
