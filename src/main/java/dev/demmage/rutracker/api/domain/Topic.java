package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
public class Topic extends BaseEntity {

    private String title;
    private List<Post> posts;
    private Attach attach;
    private Category category;

}
