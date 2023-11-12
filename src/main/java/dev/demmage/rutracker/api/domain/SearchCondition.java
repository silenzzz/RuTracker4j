package dev.demmage.rutracker.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class SearchCondition {

    private String query;
    private List<Category> categories;

}
