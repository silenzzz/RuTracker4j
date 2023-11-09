package dev.demmage.rutracker.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Giveaway extends BaseEntity {

    // TODO: 05.11.2023
    private String size;

    private List<String> link;
}
