package dev.silenzzz.rutracker.api.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Category extends BaseEntity {

    String parentName;
    String name;

    public Category(long id, String parentName, String name) {
        super(id);
        this.parentName = parentName;
        this.name = name;
    }
}
