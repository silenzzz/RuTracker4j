package dev.silenzzz.rutracker4j.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Value
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Topic extends BaseEntity {

    String title;
    List<Category> categories;
    Attach attach;

}
