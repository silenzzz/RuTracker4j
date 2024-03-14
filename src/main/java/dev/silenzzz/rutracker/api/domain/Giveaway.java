package dev.silenzzz.rutracker.api.domain;

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
public class Giveaway extends BaseEntity {

    // TODO: 05.11.2023 NOSONAR
    String size;
    List<String> link;
}
