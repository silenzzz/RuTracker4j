package dev.silenzzz.rutracker4j.scrapper.search.model;

import dev.silenzzz.rutracker4j.scrapper.search.ref.CategoryReference;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author silenzzz
 * @see <a href="http://www.silenzzz.dev">silenzzz.dev</a>
 * @see <a href="https://github.com/silenzzz">github.com/silenzzz</a>
 * @see <a href="mailto:silenzzzdev@gmail.com">silenzzz</a>
 */
@Value
@Builder
public class SearchQuery {

    List<CategoryReference> categories;
    String text;
    Pagination pagination;

}
