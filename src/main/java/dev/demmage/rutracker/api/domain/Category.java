package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseEntity {

    private String name;

}
