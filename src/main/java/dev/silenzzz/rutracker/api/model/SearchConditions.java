package dev.silenzzz.rutracker.api.model;

import dev.silenzzz.rutracker.api.domain.Category;
import lombok.Value;

import java.util.List;

@Value
public class SearchConditions {

    String query;
    List<Category> categories;

}
