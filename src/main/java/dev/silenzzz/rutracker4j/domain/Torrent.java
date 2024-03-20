package dev.silenzzz.rutracker4j.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Torrent extends BaseEntity {

    String link;
    String magnetLink;

}
