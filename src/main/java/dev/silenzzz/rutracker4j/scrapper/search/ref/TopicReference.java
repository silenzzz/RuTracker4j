package dev.silenzzz.rutracker4j.scrapper.search.ref;

import lombok.Builder;
import lombok.Value;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Value
@Builder
public class TopicReference {

    long id;
    String title;
    CategoryReference categoryReference;

}
